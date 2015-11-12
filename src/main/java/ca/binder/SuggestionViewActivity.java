/**
 * Displays profiles as cards in an adapter.
 * Uses https://github.com/Diolor/Swipecards
 */
package ca.binder;

import android.app.Activity;
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

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.*;
import ca.binder.android.DeviceInfo;
import ca.binder.domain.Suggestion;
import ca.binder.remote.Server;
import ca.binder.remote.request.GetSuggestionsRequest;


public class SuggestionViewActivity extends Activity {

	private final String LOG_TAG = "SwipeViewAcitivity";

	@Bind(R.id.swipe_view_card_container)
	SwipeFlingAdapterView flingContainer;

	private SuggestionAdapter adapter;
	private List<Suggestion> suggestionsToShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion_view);
		ButterKnife.bind(this);
		suggestionsToShow = new ArrayList<Suggestion>();


		createTestSuggestions();

		// get suggestions from api asynchronously
		//GetSuggestionsTask task = new GetSuggestionsTask();
		//task.execute(this);


		adapter = new SuggestionAdapter(this, R.layout.suggestion_card_view, suggestionsToShow);

		flingContainer.setAdapter(adapter);
		adapter.notifyDataSetChanged();

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
				// TODO
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
				Log.i(LOG_TAG, "Item " + itemPosition + " clicked!");
			}
		});

	}

	private void createTestSuggestions() {
		suggestionsToShow.add(new Suggestion("1234", "Jeff", "Computing Science", "I am a Jeff", 4, null));
		suggestionsToShow.add(new Suggestion("1235", "Also Jeff", "Business Administration", "I am another Jeff", 4, null));

	}

	/**
	 * Asynchronously performs GetSuggestions request, update list of suggestions
	 */
	class GetSuggestionsTask extends AsyncTask<Context, Void, List<Suggestion>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<Suggestion> suggestions) {
			suggestionsToShow = suggestions;
		}

		@Override
		protected List<Suggestion> doInBackground(Context... context) {
			Server server = new Server(Server.API_LOCATION, DeviceInfo.deviceId(context[0]));
			suggestionsToShow = new GetSuggestionsRequest().request(server);
			return suggestionsToShow;
		}


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
		// TODO photos
		//suggestionPhotoImageView.setImageDrawable(suggestion.getPhoto().getDrawable(getContext()));

		return card;
	}

}


