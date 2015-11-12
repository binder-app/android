/**
 * Displays profiles as cards in an adapter.
 * Uses https://github.com/Diolor/Swipecards
 */
package ca.binder;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.*;
import ca.binder.android.DeviceInfo;
import ca.binder.domain.IPhoto;
import ca.binder.domain.Suggestion;
import ca.binder.remote.Server;
import ca.binder.remote.request.GetSuggestionsRequest;


public class SwipeViewActivity extends Activity {

	private final String LOG_TAG = "SwipeViewAcitivity";

	@Bind(R.id.swipe_view_card_container)
	SwipeFlingAdapterView flingContainer;

	private ArrayAdapter<Suggestion> arrayAdapter;
	private List<Suggestion> suggestionsToShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_view);
		ButterKnife.bind(this);
		suggestionsToShow = new ArrayList<Suggestion>();
		createTestSuggestions();

		// gets suggestions from api asynchronously
		//GetSuggestionTask task = new GetSuggestionTask();
		//task.execute(getBaseContext());

//		suggestions.add(new Suggestion("id", "Joe", "CS", "Hello", 2, new IPhoto() {
//			@Override
//			public Drawable getDrawable(Context context) {
//				return null;
//			}
//		}));
//		suggestions.add(new Suggestion("id", "Joe", "CS", "Hello", 2, new IPhoto() {
//			@Override
//			public Drawable getDrawable(Context context) {
//				return null;
//			}
//		}));

		arrayAdapter = new ArrayAdapter<Suggestion>(this, R.layout.profile_card_view, R.id.userNameTextView, suggestionsToShow);


		flingContainer.setAdapter(arrayAdapter);

		/**
		 * Listener for swipe events
		 */
		flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
			@Override
			public void removeFirstObjectInAdapter() {
				// this is the simplest way to delete an object from the Adapter (/AdapterView)
				Log.d("LIST", "removed object!");
				suggestionsToShow.remove(0);
				arrayAdapter.notifyDataSetChanged();
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
				Toast.makeText(SwipeViewActivity.this, "Left!", Toast.LENGTH_SHORT).show();
				// TODO
			}

			/**
			 * Right swipe event
			 *
			 * @param dataObject
			 */
			@Override
			public void onRightCardExit(Object dataObject) {
				Toast.makeText(SwipeViewActivity.this, "Right!", Toast.LENGTH_SHORT).show();
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
	}

	class GetSuggestionTask extends AsyncTask<Context, Void, List<Suggestion>> {

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