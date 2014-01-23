package mag.joinus.activities.newmeeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import mag.joinus.app.JoinusApplication;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {
	
	private TextView textView;

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

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the date chosen by the user
		Log.v("TimePickerFragment","onTimeSet "+hourOfDay+" "+minute);
				
		DateFormat dateformat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);
		int offset = TimeZone.getDefault().getOffset(0, 0, 0, 0, 0, 0) / 1000 /3600;
		long date = (minute * 60 + (hourOfDay-offset) * 60 * 60) * 1000; 
		
		
		String formatted = 
				dateformat.format(date); // date is a long in milliseconds
	
		// meetingToCreate time is set to local time zone because of the datePickerFragment
		date = (minute * 60 + (hourOfDay) * 60 * 60) * 1000; 
		JoinusApplication.getInstance().setMeetingToCreateTime(date);
		
		textView.setText(formatted);
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	
}