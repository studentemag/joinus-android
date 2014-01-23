package mag.joinus.activities.newmeeting;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mag.joinus.app.JoinusApplication;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {

	private TextView textView;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		
		GregorianCalendar gc = new GregorianCalendar(year,month,day);
		String str = DateFormat.getDateInstance(DateFormat.MEDIUM).format(gc.getTime());
		
		// meetingToCreate date is set to 23:59 of the day before, because of timezone
		JoinusApplication.getInstance().setMeetingToCreateDate(gc.getTimeInMillis());
		
		textView.setText(str);
	}
	
	public void setTextView(TextView textView) {
		this.textView = textView;
	}

}