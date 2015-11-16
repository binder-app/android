/**
 * Displays profiles as cards in an adapter.
 * Uses https://github.com/Diolor/Swipecards
 */
package ca.binder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewSwitcher;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.binder.android.DeviceInfo;
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
//		GetSuggestionsTask task = new GetSuggestionsTask();
//		task.execute(this);
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
		});
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
				Suggestion suggestion = (Suggestion) dataObject;
				new SuggestionReactionTask().execute(new SuggestionReactionTaskParameter(SuggestionViewActivity.this, suggestion, SuggestionReaction.DISLIKE));
			}

			/**
			 * Right swipe event
			 *
			 * @param dataObject
			 */
			@Override
			public void onRightCardExit(Object dataObject) {
				// Like
				Suggestion suggestion = (Suggestion) dataObject;
				new SuggestionReactionTask().execute(new SuggestionReactionTaskParameter(SuggestionViewActivity.this, suggestion, SuggestionReaction.LIKE));
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

	private void onGetSuggestionsFailure() {
		// TODO
		Log.e("GetSuggestions", "Failed");
		dialog.dismiss();
	}

	private void onGetSuggestionsSuccess(List<Suggestion> suggestions) {
		// TODO
		suggestionsToShow = suggestions;
		Log.d("GetSuggestions", "Done!");
		Log.d("GetSuggestions", "# of suggestions: " + suggestionsToShow.size());


		adapter = new SuggestionAdapter(SuggestionViewActivity.this, R.layout.suggestion_card_view, suggestionsToShow);
		flingContainer.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		checkForEmptySuggestionList(adapter.getCount());
		dialog.dismiss();
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

	/**
	 * Asynchronously performs GetSuggestions request.
	 */
	class GetSuggestionsTask extends AsyncTask<Context, Void, List<Suggestion>> {

		private final ProgressDialog dialog = new ProgressDialog(SuggestionViewActivity.this);

		/**
		 * Show progress dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}

		/**
		 * Update suggestions collection, set up adapter and clear dialog.
		 * If no suggestions, show message.
		 * @param suggestions list of suggestions from server
		 */
		@Override
		protected void onPostExecute(List<Suggestion> suggestions) {
			suggestionsToShow = suggestions;
			Log.d("GetSuggestionsTask", "Done!");
			Log.d("GetSuggestionsTask", "# of suggestions: " + suggestionsToShow.size());


			adapter = new SuggestionAdapter(SuggestionViewActivity.this, R.layout.suggestion_card_view, suggestionsToShow);
			flingContainer.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			this.dialog.dismiss();
			checkForEmptySuggestionList(adapter.getCount());
		}

		/**
		 * Retrieves a list of suggestions from the server
		 * @param context context
		 * @return a Suggestion list
		 */
		@Override
		protected List<Suggestion> doInBackground(Context... context) {
			Log.d("GetSuggestionsTask", "Doing...");
			Server server = Server.standard(context[0]);
			Log.i("DeviceId", DeviceInfo.deviceId(context[0]));
			suggestionsToShow = new GetSuggestionsRequest().request(server);

			return suggestionsToShow;
		}


	}

	class SuggestionReactionTask extends AsyncTask<SuggestionReactionTaskParameter, Void, SuggestionReactionTaskParameter> {
		private final ProgressDialog dialog = new ProgressDialog(SuggestionViewActivity.this);


		/**
		 * Show progress dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage("Please wait...");
			this.dialog.show();
		}


		/**
		 * Sends a new like or dislike entry to the server
		 *
		 * @param param SuggestionReactionTaskParameter for the action
		 * @return param SuggestionReactionTaskParameter for the action
		 */
		@Override
		protected SuggestionReactionTaskParameter doInBackground(SuggestionReactionTaskParameter... param) {
			Log.d("suggestionReactionTask", "Doing...");
			Server server = Server.standard(param[0].context);
			Log.i("DeviceId", DeviceInfo.deviceId(param[0].context));

			if (param[0].reaction == SuggestionReaction.LIKE) {
				param[0].success = new LikeSuggestionRequest(param[0].suggestionId).request(server);
			} else if (param[0].reaction == SuggestionReaction.DISLIKE) {
				param[0].success = new DislikeSuggestionRequest(param[0].suggestionId).request(server);
			}

			return param[0];
		}


		/**
		 * Handle successful or unsuccessful like or dislike
		 * @param param SuggestionReactionTaskParameter for the action
		 */
		@Override
		protected void onPostExecute(SuggestionReactionTaskParameter param) {
			if (param.success) {
				Log.d("suggestionReactionTask", "Done!...." + param.reaction + " user " + param.suggestionId + " succeeded");
				this.dialog.dismiss();
			} else {
				Log.e("suggestionReactionTask", "Done!...." + param.reaction + " user " + param.suggestionId + " failed");
				// TODO handle a failure more gracefully. retry?
			}
		}

	}


}


