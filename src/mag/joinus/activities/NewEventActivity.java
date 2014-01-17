package mag.joinus.activities;

import java.util.Calendar;
import meg.joinus.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


/*import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;
*/

public class NewEventActivity extends FragmentActivity {
	
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		
		private TextView textView;
		
		public void setTextView(TextView textView) {
			this.textView = textView;
		}
//Perdiamo gli zeri!!!!!!!!!!!
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
//Perdiamo gli zeri!!!!!!!!!!!		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);//SERVE +1!!!!!!!!!!!!!!!!!!
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
	
	
	/*public class MapPane extends Activity {

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.map_activity);

	        // Get a handle to the Map Fragment
	        GoogleMap map = ((MapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();

	        LatLng sydney = new LatLng(-33.867, 151.206);

	        map.setMyLocationEnabled(true);
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

	        map.addMarker(new MarkerOptions()
	                .title("Sydney")
	                .snippet("The most populous city in Australia.")
	                .position(sydney));
	    }
	}*/
	

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
	    newFragment.setTextView((TextView) findViewById(R.id.picked_time));
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	public void showDatePickerDialog(View v) {
	    DatePickerFragment newFragment = new DatePickerFragment();
	    newFragment.setTextView((TextView) findViewById(R.id.picked_date));
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}

}
