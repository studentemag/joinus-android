package mag.joinus.activities.newmeeting;

import java.util.Calendar;

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
				//DateFormat.is24HourFormat(getActivity()));
				true);
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		//TODO Perdiamo gli zeri!!!!!!!!!!!
		// Do something with the time chosen by the user
		Log.v("joinUsAndroid", "onTimeSet " + hourOfDay + ":" + minute);
		textView.setText(hourOfDay + ":" + minute);
	}
}