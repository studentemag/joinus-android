package mag.joinus.activities.newmeeting;

import java.util.Locale;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	NewMeetingActivity newMeetingActivity;
	
	public SectionsPagerAdapter(FragmentManager fm, NewMeetingActivity newMeetingActivity) {
		super(fm);
		this.newMeetingActivity=newMeetingActivity;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		
		Fragment fragment = null;
		Bundle args = new Bundle();
		
		switch (position) {
		case 0:
			fragment= new NewMeetingInfoFragment();
			break;
		case 1:
			fragment = new NewMeetingMapFragment();
			break;
		}
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 2 total pages.
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return JoinusApplication.getInstance().getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return JoinusApplication.getInstance().getString(R.string.title_section2).toUpperCase(l);
		}
		return null;
	}
}