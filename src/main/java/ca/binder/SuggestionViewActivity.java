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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.binder.android.DeviceInfo;
import ca.binder.domain.Suggestion;
import ca.binder.remote.Server;
import ca.binder.remote.request.GetSuggestionsRequest;


public class SuggestionViewActivity extends Activity {

	private final String LOG_TAG = "SwipeViewAcitivity";

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

		suggestionsToShow = new ArrayList<>();


		//createTestSuggestions();

		// get suggestions from api asynchronously
		GetSuggestionsTask task = new GetSuggestionsTask();
		task.execute(this);


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
				//Do something on the left!
				//You also have access to the original object.
				//If you want to use it just cast it (String) dataObject
				Toast.makeText(SuggestionViewActivity.this, "Left!", Toast.LENGTH_SHORT).show();
				// TODO
			}

			/**
			 * Right swipe event
			 *
			 * @param dataObject
			 */
			@Override
			public void onRightCardExit(Object dataObject) {
				Toast.makeText(SuggestionViewActivity.this, "Right!", Toast.LENGTH_SHORT).show();
				// TODO
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
		suggestionsToShow.add(new Suggestion("1234", "Jeff", "Computing Science", "I am a Jeff", 4, null));
		suggestionsToShow.add(new Suggestion("1235", "Also Jeff", "Business Administration", "I am another Jeff", 4, null));

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
			Server server = new Server(Server.API_LOCATION, DeviceInfo.deviceId(context[0]));
			Log.i("DeviceId", DeviceInfo.deviceId(context[0]));
			suggestionsToShow = new GetSuggestionsRequest().request(server);

			return suggestionsToShow;
		}


	}

	/**
	 * Custom adapter inflates card layouts and places suggestion data in card view
	 */
	class SuggestionAdapter extends ArrayAdapter<Suggestion> {

		List<Suggestion> contents;

		public SuggestionAdapter(Context context, int resource, List<Suggestion> objects) {
			super(context, resource, objects);
			contents = objects;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View card = inflater.inflate(R.layout.suggestion_card_view, parent, false);

			TextView suggestionNameTextView = (TextView) card.findViewById(R.id.suggestionNameTextView);
			TextView suggestionProgramTextView = (TextView) card.findViewById(R.id.suggestionProgramTextView);
			TextView suggestionYearTextView = (TextView) card.findViewById(R.id.suggestionYearTextView);
			TextView suggestionBioTextView = (TextView) card.findViewById(R.id.suggestionBioTextView);
			ImageView suggestionPhotoImageView = (ImageView) card.findViewById(R.id.suggestionPhotoImageView);

			Suggestion suggestion = contents.get(position);

			suggestionNameTextView.setText(suggestion.getName());
			suggestionProgramTextView.setText(suggestion.getProgram());
			suggestionYearTextView.setText("Year " + suggestion.getYear());
			suggestionBioTextView.setText(suggestion.getBio());
			//TODO photo
			//suggestionPhotoImageView.setImageDrawable(suggestion.getPhoto().getDrawable(getContext()));
			return card;
		}

	}

}

