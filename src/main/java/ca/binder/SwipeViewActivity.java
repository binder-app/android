package ca.binder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SwipeViewActivity extends FragmentActivity {

	ProfileCollectionPagerAdapter profileAdapter;
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_view);

		// get profile adapter and pager
		profileAdapter = new ProfileCollectionPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.swipeViewPager);
		viewPager.setAdapter(profileAdapter);
	}

	public static class ProfileCollectionPagerAdapter extends FragmentStatePagerAdapter {
		public ProfileCollectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new ProfileFragment();
			Bundle bundle = new Bundle();
			bundle.putInt(ProfileFragment.ARG_OBJECT, position + 1);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return 100;
		}
	}

	public static class ProfileFragment extends Fragment {

		public static final String ARG_OBJECT = "object";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// The last two arguments ensure LayoutParams are inflated
			// properly.
			View rootView = inflater.inflate(R.layout.fragment_profile_view, container, false);
			Bundle args = getArguments();
			((TextView) rootView.findViewById(R.id.userNameTextView)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
			return rootView;
		}
	}
}
