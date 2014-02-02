package mag.joinus.activities.meeting;


import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.AnnotatedLatLng;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.model.UserLocation;
import mag.joinus.service.JoinusServiceImpl;
import mag.joinus.service.listeners.GetLocationsListener;
import mag.joinus.service.listeners.ShareLocationListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
public class MeetingMapFragment extends Fragment
		implements
		GetLocationsListener,
		ShareLocationListener,
		LocationListener,
		OnMyLocationButtonClickListener
        {
	
	/*
	 * Service
	 */
	private JoinusServiceImpl joinusService;
	
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	//public static final String ARG_SECTION_NUMBER = "section_number";

	private View rootView;
	private Context context;
	
    private GoogleMap mMap;
    private LocationManager mLocationManager;
	
    // Actual meeting
    private Meeting m;
    // Actual user
    private User u;

    // Toggle button
    private ToggleButton toggle;
    // Shared preferences
    private SharedPreferences settings;
    
    // Preferences file
    private static final String PREFS_FILE = "JoinusPreferences";
    
    // Preference key
    private String prefKey;
    
    // Minimum time interval between location updates, in milliseconds
//    private final long MIN_TIME = 300 * 1000;
    private final long MIN_TIME = 10 * 1000;
    
    // Minimum distance between location updates, in meters
    private final float MIN_DISTANCE = 0;
    
    
	public MeetingMapFragment() {
		super();
    	context = JoinusApplication.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("MeetingMapFragment", "onCreate for MeetingMapFragment");
		
		rootView = inflater.inflate(R.layout.fragment_meeting_map,	container, false);
		
		// Registering as MapFragment for MeetingActivity
		JoinusApplication.getInstance().setMapFragment(this);
		
		joinusService = JoinusApplication.getInstance().getService();

		
		m = JoinusApplication.getInstance().getMeeting();
		u = JoinusApplication.getInstance().getUser();
		
		// Restore preferences
		settings = context.getSharedPreferences(PREFS_FILE, 0);
		prefKey = new String("sharingOwnLocation");
		boolean sharingOwnLocation = settings.getBoolean(prefKey, false);
		Log.v("MeetingMapFragment", ".onCreate sharedPreferences: " + sharingOwnLocation);

	    // Toggle button
		toggle = (ToggleButton) rootView.findViewById(R.id.meeting_map_toggle_button);
		toggle.setChecked(sharingOwnLocation);
				
		return rootView;
	}
	
	@Override
	public void onResume() {
        super.onResume();
        Log.v("MeetingMapFragment", "onResume for MeetingMapFragment");
		joinusService.setShareLocationListener(this);
		joinusService.setGetLocationsListener(this);
		
		setupMapIfNeeded();
		
        Log.v("MeetingMapFragment", "requestLocationUpdates for MeetingMapFragment");
		// Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
//		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
      
    }

	@Override
    public void onPause() {
        super.onPause();
        
        Log.v("MeetingMapFragment", "onPause for MeetingMapFragment");
        
    }
	
	@Override
    public void onStop() {
       super.onStop();
       
       Log.v("MeetingMapFragment", "onStop for MeetingMapFragment");
       
       boolean sharingOwnLocation = toggle.isChecked();
       
       // We need an Editor object to make preference changes.
       // All objects are from android.context.Context
       settings = context.getSharedPreferences(PREFS_FILE, 0);
       Editor editor = settings.edit();
       editor.putBoolean(prefKey, sharingOwnLocation);

       // Commit the edits!
       if(editor.commit())
    	   Log.v("MeetingMapFragment", "sharedPreferences committed for MeetingMapFragment: " + sharingOwnLocation);
    
       // Remove updates from location manager and joinusServiceImpl
       mLocationManager.removeUpdates(this);
       joinusService.setShareLocationListener(null);
       joinusService.setGetLocationsListener(null);
	}
	
	private void setupMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.meeting_map))
                    .getMap();
            
            // Acquire a reference to the system Location Manager
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Log.v("MeetingMapFragment", "MeetingMapFragment reference for LocationManager" + mLocationManager);
            		
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
        		// Enables the my-location layer
        		mMap.setMyLocationEnabled(true);
        		mMap.setOnMyLocationButtonClickListener(this);
            }
        }
    }
	
	/**
     * Implementation of {@link LocationListener}.
     */
	@Override
	public void onLocationChanged(Location loc) {
		Log.v("MeetingMapFragment", ".onLocationChanged - location update: " + loc);
		LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
		
		boolean sharingOwnLocation = toggle.isChecked();
		
		// Share location
    	if (sharingOwnLocation && !m.getGuests().contains(u)) {
    		Log.v("MeetingMapFragment", "toggle ON -> sharing location");
    		
			UserLocation userLoc = new UserLocation();
	    	userLoc.setLatLng(new AnnotatedLatLng(latLng));
	    	userLoc.setUser(u);
	    	long timestamp = (new Date()).getTime();
	    	userLoc.setTimestamp(timestamp);
	    	joinusService.shareLocation(u.getPhone(), userLoc);
		} else {
			Log.v("MeetingMapFragment", "toggle OFF -> nothing to share");
		}
    	
    	//Ask location for users
    	joinusService.getLocations(m.getId());    	

	}

	@Override
	public void onProviderDisabled(String arg0) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	public boolean onMyLocationButtonClick() {
		Log.v("MeetingMapFragment", "MyLocationButton clicked for MeetingMapFragment");
		
		// Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
		return false;
	}

	@Override
	public void onLocationShared() {
		Log.v("MeetingMapFragment", ".onLocationShared");
		//Toast.makeText(context, "You just shared your location!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLocationsRetrieved(List<UserLocation> uLocs) {
		Log.v("MeetingMapFragment", ".onLocationsRetrieved");
		
		mMap.clear();
		
		LatLngBounds.Builder bounds = new LatLngBounds.Builder();
		
		int i = 1;
		for (UserLocation loc : uLocs) {
			
			long now = new Date().getTime();
	    	long timestamp = loc.getTimestamp();
	    	long millis = now - timestamp;
  	
	    	String diff = String.format("%d min, %d sec ", 
	    		    TimeUnit.MILLISECONDS.toMinutes(millis),
	    		    TimeUnit.MILLISECONDS.toSeconds(millis) - 
	    		    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
	    	);
	    	
			mMap.addMarker(new MarkerOptions()
				.position(loc.getLatLng().toLatLng())
    			.title(loc.getUser().getName())
    			.snippet(diff + getString(R.string.meeting_map_ago))
    			.icon(BitmapDescriptorFactory.defaultMarker(i * 360 / (uLocs.size()+1) )));
			
			bounds.include(loc.getLatLng().toLatLng());
			
			i++;
		}
		
		// Drawing marker for destination
    	LatLng destinationLatLng = m.getLatLng().toLatLng();
    	mMap.addMarker(new MarkerOptions()
    			.position(destinationLatLng)
    			.title(getString(R.string.meeting_map_destination))
    			.icon(BitmapDescriptorFactory.defaultMarker(0)))
    			.showInfoWindow();
	   	bounds.include(destinationLatLng);	   	

		mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 10));
		
		Log.v("MeetingMapFragment", ".onLocationsRetrieved move camera with bounds");
	}
}