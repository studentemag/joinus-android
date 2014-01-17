package meg.joinus.activities;

import meg.joinus.R;
import meg.joinus.model.Event;
import meg.joinus.model.JoinUs;
import meg.joinus.model.JoinUsServiceUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UpcomingEventsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming_events);
		
		Log.v("joinUsAndroid", "starting UpcomingEventsActivity");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ListView listview = (ListView) findViewById(R.id.listview);
		EventArrayAdapter adapter = new EventArrayAdapter(
				this,android.R.layout.simple_list_item_1, 
				JoinUsServiceUtil.getUpcomingEvents());
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(
			new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
		                  int position, long id) {
					Event event = (Event) parent.getItemAtPosition(position);
					Log.v("joinUsAndroid", "click on "+event.getTitle());
				}	
			}
		);
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
		Intent intent = new Intent(this, NewEventActivity.class);
		startActivity(intent);
	}

}