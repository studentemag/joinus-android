package mag.joinus.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Location;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


public class NewEventActivity extends FragmentActivity implements Observer {
	
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		
		private TextView textView;
		
		public void setTextView(TextView textView) {
			this.textView = textView;
		}
//TODO Perdiamo gli zeri!!!!!!!!!!!
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					//DateFormat.is24HourFormat(getActivity()));
					true);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			Log.v("joinUsAndroid", "onTimeSet " + hourOfDay + ":" + minute);
			textView.setText(hourOfDay + ":" + minute);
		}
	}
	
	public static class DatePickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

		private TextView textView;
		
		public void setTextView(TextView textView) {
			this.textView = textView;
		}
//TODO Perdiamo gli zeri!!!!!!!!!!!		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);//TODO SERVE +1!!!!!!!!!!!!!!!!!!
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			Log.v("joinUsAndroid", "onDateSet " + year + "-" + month + "-" + day);
			textView.setText(year + "-" + month + "-" + day);
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_new_event);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_event, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_done:
	        	createEvent();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void createEvent() {
		String title = ((EditText) findViewById(R.id.meeting_title)).toString();
		long date = 10;//TODO new Date();
		Location l = new Location(10,10);
		User mc = new User("mario");
		List<String> phones = new ArrayList<String>();
		phones.add("339142143");
		phones.add("32143545435");
		
		Log.v("joinUsAndroid", "creating event");
		JoinusApplication.getInstance().getService().createMeeting(this, title, date, l, mc, phones);
		//TODO alternativa è avere un membro application oppure:
		/*JoinusApplication app = JoinusApplication.getInstance();
		app.getService().createMeeting(title, date, l, mc, phones);*/
	}
	
	public void showTimePickerDialog(View v) {
	    TimePickerFragment newFragment = new TimePickerFragment();
	    newFragment.setTextView((TextView) findViewById(R.id.picked_time));
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	public void showDatePickerDialog(View v) {
	    DatePickerFragment newFragment = new DatePickerFragment();
	    newFragment.setTextView((TextView) findViewById(R.id.picked_date));
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void onMeetingCreated(Meeting meet) {
		//Do something
	}

	@Override
	public void update(Observable sogg, Object arg) {
		if (sogg instanceof MeetingWrapper) {
			meet = ((MeetingWrapper) sogg).getMeeting();
		}
	}
	
	private Meeting meet;
}
