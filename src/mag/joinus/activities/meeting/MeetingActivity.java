package mag.joinus.activities.meeting;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Meeting;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

public class MeetingActivity extends FragmentActivity implements
		ActionBar.TabListener {
	
	/*
	 * Messages required to start activity
	 */
	/*public static final String MEETING_ID = "meeting_id";

	private int meetingId;*/
	
	// Actual meeting
	private Meeting m;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting);
		
		// Registering as MeetingActivity for application context
		JoinusApplication.getInstance().setMeetingActivity(this);
		
		m = JoinusApplication.getInstance().getMeeting();

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle(m.getTitle());

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager()/*, meetingId*/);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meeting, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	public void accept(View view) {
		JoinusApplication.getInstance().getInfoFragment().accept(view);
	}
	
	public void deny(View view) {
		JoinusApplication.getInstance().getInfoFragment().deny(view);
	}
	
//	public void onToggleClicked(View view) {
//	    // Is the toggle on?
//	    boolean on = ((ToggleButton) view).isChecked();
//	    
//	    if (on) {
//	    	// The toggle is enabled
//	    	JoinusApplication.getInstance().getMapFragment().setSharingOwnLocation(true);
//        	Log.v("joinusandroid", "toggle ON");
//	    } else {
//	    	// The toggle is disabled
//	    	JoinusApplication.getInstance().getMapFragment().setSharingOwnLocation(false);
//        	Log.v("joinusandroid", "toggle OFF");
//	    }
//	}
}
