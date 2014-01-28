package mag.joinus.activities.meeting;


import java.util.List;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Meeting;
import mag.joinus.model.UserLocation;
import mag.joinus.service.JoinusServiceImpl;
import mag.joinus.service.listeners.GetLocationsListener;
import mag.joinus.service.listeners.ShareLocationListener;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
//O		ConnectionCallbacks,
//O        OnConnectionFailedListener
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
//O    private LocationClient mLocationClient;
    
    // These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
//O    private static final LocationRequest REQUEST = LocationRequest.create()
//            .setInterval(5000)         // 5 seconds
//            .setFastestInterval(16)    // 16ms = 60fps
//O            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//PRIORITY_NO_POWER
	
    //minimum time interval between location updates, in milliseconds
    private final long MIN_TIME = 300 * 1000;
    //minimum distance between location updates, in meters
    private final float MIN_DISTANCE = 3 * 1000;
    
    
	public MeetingMapFragment() {
		super();
    	context = JoinusApplication.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_meeting_map,	container, false);
		
		joinusService = JoinusApplication.getInstance().getService();
		joinusService.setShareLocationListener(this);
		joinusService.setGetLocationsListener(this);
		
		/*TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_map);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));*/
		
//		setUpMapIfNeeded();
		// TODO onLocationChanged(Location loc);share & get
		
		return rootView;
	}
	
	@Override
	public void onResume() {
        super.onResume();
        
        Log.v("Joinus android", "onResume called");
        setUpMapIfNeeded();
//O        setUpLocationClientIfNeeded();
//O        mLocationClient.connect();
        setUpMeetingLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
//O        if (mLocationClient != null) {
//O            mLocationClient.disconnect();
//O        }
    }
	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.meeting_map))
                    .getMap();
            
            // Acquire a reference to the system Location Manager
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            		
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
	private void setUpMap() {
		// Enables the my-location layer
		mMap.setMyLocationEnabled(true);
		mMap.setOnMyLocationButtonClickListener(this);

        // Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

        
//      mMap.setOnMapLongClickListener(this);        
/*        // TODO lastKnownLocation is null and raises nullPointerException
        //android.location.Location lastKnownLocation = mLocationManager.getLastKnownLocation(Context.LOCATION_SERVICE);
        //LatLng ll = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 4.0f));*/
    }
	
//O	private void setUpLocationClientIfNeeded() {
//O        if (mLocationClient == null) {
//O            mLocationClient = new LocationClient(
//O            		context,
//O                    this,  // ConnectionCallbacks
//O                    this); // OnConnectionFailedListener
//O        }
//O    }
	
	public void setUpMeetingLocation(/*String address, LatLng latLng*/) {
    	
    	Meeting m = JoinusApplication.getInstance().getMeeting();
//    	String address = m.getAddress();
    	LatLng latLng = m.getLatLng().toLatLng();
    
    	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

    	mMap.addMarker(new MarkerOptions().position(latLng)).setTitle("Destination");
    }

	private void searchForAddress(Location l) {
//    	new GetAddressTask(this, context).execute(l);
    }
	
	/**
     * Implementation of {@link LocationListener}.
     */
	@Override
	public void onLocationChanged(Location loc) {
		// TODO scegliere momento x clear
    	mMap.clear();
    	Log.v("Joinus android", "Location update: " + loc);
//    	LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
		// Control share, share & get
	}

	@Override
	public void onProviderDisabled(String arg0) {
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
	public boolean onMyLocationButtonClick() {
		// Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
		return false;
	}

	@Override
	public void onLocationShared() {
		Toast.makeText(context, "You just shared your location!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLocationsRetrieved(List<UserLocation> uLocs) {
		// TODO Auto-generated method stub
		Log.v("joinusandroid", "Participant locations retrieved");
	}

//O	@Override
//O	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
//O	}

	/**
     * Callback called when connected to GCore. Implementation of {@link ConnectionCallbacks}.
     */
//O	@Override
//O	public void onConnected(Bundle connectionHint) {
/*		mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener */
//O	}

	/**
     * Callback called when disconnected from GCore. Implementation of {@link ConnectionCallbacks}.
     */
//O	@Override
//O	public void onDisconnected() {
		 // Do nothing
//O	}
}