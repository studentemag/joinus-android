package mag.joinus.activities.upcomingmeetings;

import java.util.List;

import mag.joinus.R;
import mag.joinus.activities.meeting.MeetingActivity;
import mag.joinus.activities.newmeeting.NewMeetingActivity;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Meeting;
import mag.joinus.service.JoinusServiceImpl;
import mag.joinus.service.listeners.GetMeetingListListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UpcomingEventsActivity extends Activity implements GetMeetingListListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming_events);
		
		Log.v("joinUsAndroid", "starting UpcomingEventsActivity");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ListView listview = (ListView) findViewById(R.id.listview);
		
		// TODO va selezionato l'utente dal login
		String phone = JoinusApplication.getInstance().getUser().getPhone();
		
		joinusServiceImpl = JoinusApplication.getInstance().getService();
		joinusServiceImpl.setGetMeetingListListener(this);
		
		populateList(joinusServiceImpl.getUpcomingEvents(phone));
		
		listview.setOnItemClickListener(
			new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
		                  int position, long id) {
					Meeting meeting = (Meeting) parent.getItemAtPosition(position);
					Log.v("joinUsAndroid", "click on "+meeting.getTitle());
					Context c = JoinusApplication.getInstance().getApplicationContext();
					Intent intent = new Intent(c, MeetingActivity.class);
					intent.putExtra(MeetingActivity.MEETING_ID, meeting.getId());
					startActivity(intent);
					
				}	
			}
		);
	}

	private void populateList(List<Meeting> mList) {
		ListView listview = (ListView) findViewById(R.id.listview);
		
		EventArrayAdapter adapter = new EventArrayAdapter(
				this,android.R.layout.simple_list_item_1, 
				mList);
		
		listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upcoming_events, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_newEvent:
	        	startNewEventActivity();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void startNewEventActivity(){
		Intent intent = new Intent(this, NewMeetingActivity.class);
		startActivity(intent);
	}

	private JoinusServiceImpl joinusServiceImpl;

	@Override
	public void onMeetingListRetrieved(List<Meeting> mList) {
		Log.v("joinUsAndroid", "Meeting list received");
		
		populateList(mList);
	}
}