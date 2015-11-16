/**
 * Displays profiles as cards in an adapter.
 * Uses https://github.com/Diolor/Swipecards
 */
package ca.binder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewSwitcher;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.binder.domain.Suggestion;
import ca.binder.domain.SuggestionReaction;
import ca.binder.remote.Callback;
import ca.binder.remote.Server;
import ca.binder.remote.request.AsyncServerRequest;
import ca.binder.remote.request.DislikeSuggestionRequest;
import ca.binder.remote.request.GetSuggestionsRequest;
import ca.binder.remote.request.LikeSuggestionRequest;


public class SuggestionViewActivity extends Activity {

	private final String LOG_TAG = "SwipeViewAcitivity";
	private ProgressDialog dialog;

	@Bind(R.id.swipe_view_card_container)
	SwipeFlingAdapterView flingContainer;
	ViewSwitcher viewSwitcher;

	private SuggestionAdapter adapter;
	private List<Suggestion> suggestionsToShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion_view);
		ButterKnife.bind(this);

		viewSwitcher = (ViewSwitcher) findViewById(R.id.suggestionViewSwitcher);
		dialog = new ProgressDialog(SuggestionViewActivity.this);
		suggestionsToShow = new ArrayList<>();


		//createTestSuggestions();

		// get suggestions from api asynchronously
		Server server = Server.standard(this);
		new AsyncServerRequest<>(server, new GetSuggestionsRequest(), new Callback<List<Suggestion>>() {
			@Override
			public void use(List<Suggestion> suggestions) {
				//Executed after request finishes
				if (suggestions != null) {
					onGetSuggestionsSuccess(suggestions);
				} else {
					onGetSuggestionsFailure();
				}
			}
		}).run();
		this.dialog.setMessage("Loading...");
		this.dialog.show();


		/**
		 * Listener for swipe events
		 */
		flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
			@Override
			public void removeFirstObjectInAdapter() {
				// this is the simplest way to delete an object from the Adapter (/AdapterView)
				Log.d("LIST", "removed object!");
				suggestionsToShow.remove(0);
				adapter.notifyDataSetChanged();
			}

			/**
			 * Left swipe event
			 *
			 * @param dataObject
			 */
			@Override
			public void onLeftCardExit(Object dataObject) {
				// Dislike
				final Suggestion suggestion = (Suggestion) dataObject;

				Server server = Server.standard(SuggestionViewActivity.this);
				new AsyncServerRequest<>(server, new DislikeSuggestionRequest(suggestion.getId()), new Callback<Boolean>() {
					@Override
					public void use(Boolean success) {
						//Executed after request finishes
						if (success) {
							onSendSuggestionReactionSuccess(suggestion, SuggestionReaction.DISLIKE);
						} else {
							onSendSuggestionReactionFailure(suggestion, SuggestionReaction.DISLIKE);
						}
					}
				}).run();
				dialog.setMessage("Please wait...");
				dialog.show();
			}

			/**
			 * Right swipe event
			 *
			 * @param dataObject
			 */
			@Override
			public void onRightCardExit(Object dataObject) {
				// Like
				final Suggestion suggestion = (Suggestion) dataObject;

				Server server = Server.standard(SuggestionViewActivity.this);
				new AsyncServerRequest<>(server, new LikeSuggestionRequest(suggestion.getId()), new Callback<Boolean>() {
					@Override
					public void use(Boolean success) {
						//Executed after request finishes
						if (success) {
							onSendSuggestionReactionSuccess(suggestion, SuggestionReaction.LIKE);
						} else {
							onSendSuggestionReactionFailure(suggestion, SuggestionReaction.LIKE);
						}
					}
				}).run();
				dialog.setMessage("Please wait...");
				dialog.show();
			}

			@Override
			public void onAdapterAboutToEmpty(int itemsInAdapter) {
				Log.d(LOG_TAG, "Adapter about to empty.." + itemsInAdapter + "items");
				checkForEmptySuggestionList(itemsInAdapter);
			}

			@Override
			public void onScroll(float scrollProgressPercent) {
				// do nothing
			}
		});


		// Optionally add an OnItemClickListener
		flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
			@Override
			public void onItemClicked(int itemPosition, Object dataObject) {
				Log.i(LOG_TAG, "Suggestion " + itemPosition + " clicked! Id:" + adapter.contents.get(itemPosition).getId());
			}
		});

	}

	/**
	 * Handler when a like or dislike request completed successfully
	 *
	 * @param suggestion suggestion the operation was performed for
	 * @param reaction   like or dislike
	 */
	private void onSendSuggestionReactionSuccess(Suggestion suggestion, SuggestionReaction reaction) {
		Log.d("Suggestion Reaction", reaction + " user " + suggestion.getId() + " succeeded");
		dialog.dismiss();
	}

	/**
	 * Handler when a like or dislike request fails
	 *
	 * @param suggestion suggestion the operation was performed for
	 * @param reaction   like or dislike
	 */
	private void onSendSuggestionReactionFailure(Suggestion suggestion, SuggestionReaction reaction) {
		Log.e("Suggestion Reaction", reaction + " user " + suggestion.getId() + " failed");
		// TODO handle a failure more gracefully. retry?
	}

	private void onGetSuggestionsSuccess(List<Suggestion> suggestions) {
		suggestionsToShow = suggestions;
		Log.d("GetSuggestions", "Getting suggestions succeeded!");
		Log.d("GetSuggestions", "# of suggestions: " + suggestionsToShow.size());

		adapter = new SuggestionAdapter(SuggestionViewActivity.this, R.layout.suggestion_card_view, suggestionsToShow);
		flingContainer.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		checkForEmptySuggestionList(adapter.getCount());
		dialog.dismiss();
	}

	private void onGetSuggestionsFailure() {

		Log.e("GetSuggestions", "Getting suggestions failed");
		dialog.dismiss();
		// TODO handle failure
	}


	/**
	 * Determines whether the list of suggestions is empty, and pages the ViewSwitcher to show the empty message if so.
	 * @param numItems the number of items in the adapter
	 */
	private void checkForEmptySuggestionList(int numItems) {
		if (numItems == 0) {
			Log.d("Suggestions empty?", "empty");
			viewSwitcher.showNext();

		} else {
			Log.d("Suggestions empty?", "not empty");
			//viewSwitcher.showPrevious();
		}
	}

	private void createTestSuggestions() {
		suggestionsToShow.add(new Suggestion("1234", "Jeff", "Computing Science", "I am a Jeff", "Year 4", null));
		suggestionsToShow.add(new Suggestion("1235", "Also Jeff", "Business Administration", "I am another Jeff", "Year 3", null));

	}


}


