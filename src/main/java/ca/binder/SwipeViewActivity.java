/**
 * Displays profiles as cards in an adapter.
 * Uses https://github.com/Diolor/Swipecards
 */
package ca.binder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.*;


public class SwipeViewActivity extends Activity {

	private final String LOG_TAG = "SwipeViewAcitivity";
	@Bind(R.id.swipe_view_card_container)
	SwipeFlingAdapterView flingContainer;
	private ArrayAdapter<String> arrayAdapter;
	private ArrayList<String> profiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_view);
		ButterKnife.bind(this);

		profiles = new ArrayList<>();
		profiles.add("test1");
		profiles.add("test2");
		profiles.add("test3");
		profiles.add("test4");

		arrayAdapter = new ArrayAdapter<>(this, R.layout.profile_card_view, R.id.userNameTextView, profiles);


		flingContainer.setAdapter(arrayAdapter);

		/**
		 * Listener for swipe events
		 */
		flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
			@Override
			public void removeFirstObjectInAdapter() {
				// this is the simplest way to delete an object from the Adapter (/AdapterView)
				Log.d("LIST", "removed object!");
				profiles.remove(0);
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



}
