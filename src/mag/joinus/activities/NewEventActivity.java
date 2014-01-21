package mag.joinus.activities;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Meeting;
import mag.joinus.service.JoinusService;
import mag.joinus.service.listeners.CreateMeetingListener;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class NewEventActivity extends FragmentActivity implements
		OnMarkerDragListener, 
		OnMapLongClickListener,
		OnMyLocationButtonClickListener,
		LocationListener,
		CreateMeetingListener {
	
	/*
	 * Meeting parameters
	 */
	private String title;
	private String address;
	private LatLng location;
	
	
    private GoogleMap mMap;
    private LocationManager mLocationManager;
	
	public static class TimePickerFragment extends DialogFragment implements
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
					DateFormat.is24HourFormat(getActivity()));
					//true);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			//TODO Perdiamo gli zeri!!!!!!!!!!!
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

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			Log.v("joinUsAndroid", "onDateSet " + year + "-" + month + "-" + day);
			// TODO FORMATTARE DATA +1!!!!!!!!!!!!!!!!!!
			textView.setText(year + "-" + month + "-" + day);
		}
	}
	
	/**
	 * A subclass of AsyncTask that calls getFromLocation() in the
	 * background. The class definition has these generic types:
	 * Location - A Location object containing
	 * the current location.
	 * Void     - indicates that progress units are not used
	 * String   - An address passed to onPostExecute()
	 */
	private class GetAddressTask extends AsyncTask<Location, Void, String> {
		Context mContext;
		LatLng latLng;
		
		public GetAddressTask(Context context) {
			super();
			mContext = context;
		}
		
		/**
		 * Get a Geocoder instance, get the latitude and longitude
		 * look up the address, and return it
		 *
		 * @params params One or more Location objects
		 * @return A string containing the address of the current
		 * location, or an empty string if no address can be found,
		 * or an error message
		 */
		@Override
		protected String doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			// Get the current location from the input parameter list
			Location loc = params[0];
			latLng=new LatLng(loc.getLatitude(),loc.getLongitude());
			// Create a list to contain the result address
			List<Address> addresses = null;
			try {
				/*
				 * Return 1 address.
				 */
				addresses = geocoder.getFromLocation(loc.getLatitude(),
						loc.getLongitude(), 1);
			} catch (IOException e1) {
				Log.e("LocationSampleActivity",
						"IO Exception in getFromLocation()");
				e1.printStackTrace();
				return ("IO Exception trying to get address");
			} catch (IllegalArgumentException e2) {
				// Error message to post in the log
				String errorString = "Illegal arguments " +
						Double.toString(loc.getLatitude()) +
						" , " +
						Double.toString(loc.getLongitude()) +
						" passed to address service";
				Log.e("LocationSampleActivity", errorString);
				e2.printStackTrace();
				return errorString;
			}
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0) {
				// Get the first address
				Address address = addresses.get(0);
				/*
				 * Format the first line of address (if available),
				 * city, and country name.
				 */
				String addressText = String.format(
						"%s, %s, %s",
						// If there's a street address, add it
						address.getMaxAddressLineIndex() > 0 ?
								address.getAddressLine(0) : "",
								// Locality is usually a city
								address.getLocality(),
								// The country of the address
								address.getCountryName());
				// Return the text
				return addressText;
			} else {
				return "No address found";
			}
		}
	
		@Override
		protected void onPostExecute (String address) {
			setUpMeetingLocation(address,latLng);
		}
	}

	private class GetLocationTask extends AsyncTask<String, Void, Location> {
		Context mContext;
		String stringAddress;
		
		public GetLocationTask(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected Location doInBackground(String... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			// Get the current location from the input parameter list
			stringAddress = params[0];
			// Create a list to contain the result address
			List<Address> addresses = null;
			try {
				/*
				 * Return 1 address.
				 */
				addresses = geocoder.getFromLocationName(stringAddress, 1);
			} catch (IOException e1) {
				Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
				e1.printStackTrace();
				return null;
			} catch (IllegalArgumentException e2) {
				// Error message to post in the log
				String errorString = "Illegal argument: " +stringAddress+
						" passed to address service";
				Log.e("LocationSampleActivity", errorString);
				e2.printStackTrace();
				return null;
			}
			
			Location l = new Location("GeoCoder");
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0) {
				// Get the first address
				Address address = addresses.get(0);
				l.setLatitude(address.getLatitude());
				l.setLongitude(address.getLongitude());
			}
			return l;
		}
		
		@Override
		protected void onPostExecute (Location l) {
			LatLng latLng = new LatLng(l.getLatitude(),l.getLongitude());
			setUpMeetingLocation(stringAddress,latLng);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_new_event);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		EditText meetingAddress = (EditText) this.findViewById(R.id.meeting_address);
		meetingAddress.setOnEditorActionListener(
				new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
							searchForLocation(v.getText().toString());
							return true;
						}
						return false;
		    			}
				});
		
		setUpMapIfNeeded();
		
		joinusService = JoinusApplication.getInstance().getService();

	}
	
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            
            // Acquire a reference to the system Location Manager
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            		
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);

        // Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        // TODO lastKnownLocation is null and raises nullPointerException
        //android.location.Location lastKnownLocation = mLocationManager.getLastKnownLocation(Context.LOCATION_SERVICE);
        //LatLng ll = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 4.0f));
    }
    
    public void setUpMeetingLocation(String address, LatLng latLng){
    	
    	this.address = address;
    	this.location = latLng;
    	EditText textbox = (EditText) findViewById(R.id.meeting_title);
    	this.title=textbox.getText().toString();
    	
    	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
    	((EditText) findViewById(R.id.meeting_address)).setText(address);
    	
    	mMap.clear(); 
    	mMap.addMarker(new MarkerOptions().position(latLng));
    }

    private void searchForLocation(String address){
    	new GetLocationTask(this.getApplicationContext()).execute(address);
    }
    
    private void searchForAddress(Location l){
    	new GetAddressTask(this.getApplicationContext()).execute(l);
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
	        case R.id.new_event_action_done:
	        	createEvent();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

	public void createEvent() {
		Log.v("joinUsAndroid", "creating event");
		joinusService.createMeeting(this, title, 0, location, null, null);
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

	@Override
	public void onMapLongClick(LatLng arg0) {
		Log.v("newEventActivity","onMapLongClick"+arg0.toString());
		Location l = new Location(LocationManager.NETWORK_PROVIDER);
		l.setLatitude(arg0.latitude);
		l.setLongitude(arg0.longitude);
		this.searchForAddress(l);
	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub
		Log.v("newEventActivity","onMarkerDrag"+arg0.toString());
	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub
		Log.v("newEventActivity","onMarkerDragEnd"+arg0.toString());
		
	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		Log.v("newEventActivity","onMarkerDragStart"+arg0.toString());
	}

	@Override
	public boolean onMyLocationButtonClick() {
		// TODO Auto-generated method stub
		Log.v("newEventActivity","onMyLocationButtonClic");
		return false;
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(android.location.Location location) {
		Log.v("newEventActivity","onLocationChanged"+location.toString());
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
		mLocationManager.removeUpdates(this);
	}

	public void onMeetingCreated(Meeting meet) {
		//Do something
		Log.v("joinUsAndroid",meet.getTitle());
	}
	
	private JoinusService joinusService;

}
