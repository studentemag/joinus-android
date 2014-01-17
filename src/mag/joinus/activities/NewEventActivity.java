package mag.joinus.activities;

import java.util.Calendar;

import meg.joinus.R;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewEventActivity extends FragmentActivity {
	
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		
		private TextView textView;

		public TimePickerFragment(){};
		
		public void setTextView(TextView textView) {
			this.textView = textView;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			Log.v("joinUsAndroid", "onTimeSet"+hourOfDay+" "+minute);
			textView.setText(hourOfDay+" "+minute);
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
	
	public void showTimePickerDialog(View v) {
	    TimePickerFragment newFragment = new TimePickerFragment();
	    newFragment.setTextView((TextView) findViewById(R.id.picked_date));
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}

}
