package mag.joinus.activities.newmeeting;


import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Meeting;
import mag.joinus.service.JoinusService;
import mag.joinus.service.listeners.CreateMeetingListener;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewEventActivity extends FragmentActivity implements
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
    	new GetLocationTask(this, this.getApplicationContext()).execute(address);
    }
    
    private void searchForAddress(Location l){
    	new GetAddressTask(this, this.getApplicationContext()).execute(l);
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
		// TODO Auto-generated method stub
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
