package mag.joinus.activities.meeting;


import java.util.ArrayList;
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
import android.widget.CompoundButton;
import android.widget.Toast;
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
	
    // Actual meeting
    private Meeting m;
    // Actual user
    private User u;
//    // Reference to MeetingActivity
//    MeetingActivity meetingActivity;
    // Participant locations
    private List<UserLocation> locations;
    // Number of participants
    private int numParticipants;
    // First location update
    private boolean firstLocUpdate = true;
    // First map drawing
    private boolean firstMapDrawing = true;
    // Toggle button
    private ToggleButton toggle;
    // Toggle value
    private boolean sharingOwnLocation;
    // Shared preferences
    private SharedPreferences settings;
    
    // Preferences file
    private static final String PREFS_FILE = "JoinusPreferences";
    // Preference key
    private String prefKey;
    // Minimum time interval between location updates, in milliseconds
//    private final long MIN_TIME = 300 * 1000;
    private final long MIN_TIME = 90 * 1000;
    // Minimum distance between location updates, in meters
//    private final float MIN_DISTANCE = 3 * 1000;
    private final float MIN_DISTANCE = 0;
    
    
	public MeetingMapFragment() {
		super();
    	context = JoinusApplication.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("Joinusandroid", "onCreate for MeetingMapFragment");
		
		rootView = inflater.inflate(R.layout.fragment_meeting_map,	container, false);
		
		// Registering as MapFragment for MeetingActivity
		JoinusApplication.getInstance().setMapFragment(this);
//		// Getting reference to Meeting Activity
//		meetingActivity = JoinusApplication.getInstance().getMeetingActivity();
		
		joinusService = JoinusApplication.getInstance().getService();
		joinusService.setShareLocationListener(this);
		joinusService.setGetLocationsListener(this);
		
		m = JoinusApplication.getInstance().getMeeting();
		u = JoinusApplication.getInstance().getUser();
		locations = new ArrayList<UserLocation>();
		
		// Restore preferences
		settings = context.getSharedPreferences(PREFS_FILE, 0);
//		this.getActivity().getSharedPreferences(PREFS_FILE, 0);
		prefKey = new String("sharingOwnLocation_" + m.getId());
		sharingOwnLocation = settings.getBoolean(prefKey, false);
		Log.v("Joinusandroid", "sharedPreferences for MeetingMapFragment: " + sharingOwnLocation);
		
		// Applying preferences
		toggle = (ToggleButton) rootView.findViewById(R.id.meeting_map_toggle_button);
		toggle.setChecked(sharingOwnLocation);
		
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            // The toggle is enabled
		        	sharingOwnLocation = true;
		        	Log.v("joinusandroid", "toggle ON");
		        } else {
		            // The toggle is disabled
		        	sharingOwnLocation = false;
		        	Log.v("joinusandroid", "toggle OFF");
		        }
		    }
		});

		
		/*TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_map);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));*/
				
		return rootView;
	}
	
	@Override
	public void onResume() {
        super.onResume();
        
        Log.v("Joinusandroid", "onResume for MeetingMapFragment");
        setUpMapIfNeeded();
//O        setUpLocationClientIfNeeded();
//O        mLocationClient.connect();
        mMap.clear();
        setUpMeetingLocation();
        setUpParticipantLocations();
        
    }

	@Override
    public void onPause() {
        super.onPause();
        
        Log.v("Joinusandroid", "onPause for MeetingMapFragment");
        
//O        if (mLocationClient != null) {
//O            mLocationClient.disconnect();
//O        }
    }
	
	@Override
    public void onStop() {
       super.onStop();
       
       Log.v("Joinusandroid", "onStop for MeetingMapFragment");
       
       // We need an Editor object to make preference changes.
       // All objects are from android.context.Context
       settings = context.getSharedPreferences(PREFS_FILE, 0);
       Editor editor = settings.edit();
       editor.putBoolean(prefKey, sharingOwnLocation);

       // Commit the edits!
       if(editor.commit())
    	   Log.v("Joinusandroid", "sharedPreferences committed for MeetingMapFragment: " + sharingOwnLocation);
    }
	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.meeting_map))
                    .getMap();
            
            // Acquire a reference to the system Location Manager
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Log.v("Joinusandroid", "MeetingMapFragment reference for LocationManager" + mLocationManager);
            		
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
	private void setUpMap() {
		Log.v("Joinusandroid", "setUpMap for MeetingMapFragment");
		
		// Enables the my-location layer
		mMap.setMyLocationEnabled(true);
		mMap.setOnMyLocationButtonClickListener(this);

        // Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
//		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        Log.v("Joinusandroid", "requestLocationUpdates for MeetingMapFragment");
        
/*        //lastKnownLocation is null and raises nullPointerException
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
	
	private void setUpMeetingLocation() {
		Log.v("Joinusandroid", "setUpMeetingLocation for MeetingMapFragment");
		
    	LatLng latLng = m.getLatLng().toLatLng();

    	mMap.addMarker(new MarkerOptions()
    			.position(latLng)
    			.title("Destination")
    			.icon(BitmapDescriptorFactory.defaultMarker(0)))
    			.showInfoWindow();
    	
    	if (firstMapDrawing) {
    		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
			firstMapDrawing = false;
		}
    }

	private void setUpParticipantLocations() {
		Log.v("Joinusandroid", "setUpParticipantsLocation for MeetingMapFragment");
		numParticipants = m.getParticipants().size();

		// Service request
    	joinusService.getLocations(m.getId());
		
// TODO marker qui o in onretrieved?
	}

	private void searchForAddress(Location l) {
//    	new GetAddressTask(this, context).execute(l);
    }
	
	/**
     * Implementation of {@link LocationListener}.
     */
	@Override
	public void onLocationChanged(Location loc) {
		Log.v("Joinusandroid", "Location update: " + loc);
		
    	final LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
//*****    	LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());

//*********************
		if (firstLocUpdate) {
			firstLocUpdate = false;
		} else {
			mMap.clear();
			setUpMeetingLocation();
			setUpParticipantLocations();
		}    	
    	
    	mMap.addMarker(new MarkerOptions()
		    	.position(latLng)
				.title(String.format(rootView.getResources().getString(R.string.meeting_map_you)))//.setTitle(u.getName)
//				.icon(BitmapDescriptorFactory.defaultMarker(numParticipants * 360 / (numParticipants + 1))));
				.icon(BitmapDescriptorFactory.defaultMarker(numParticipants * 360 / (numParticipants + 1))));

    	
    	
    	
//*************    	
			final LatLng destination = m.getLatLng().toLatLng();
			// Pan to see all markers in view.
	        // Cannot zoom to bounds until the map has a size.
//	        final View mapView = rootView;//getSupportFragmentManager().findFragmentById(R.id.meeting_map).getView();
//	        if (mapView.getViewTreeObserver().isAlive()) {
//	            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//	                @SuppressWarnings("deprecation") // We use the new method when supported
//	                @SuppressLint("NewApi") // We check which build version we are using.
//	                @Override
//	                public void onGlobalLayout() {
	                    LatLngBounds.Builder bounds = new LatLngBounds.Builder();
	                    
	                    for (UserLocation uLoc : locations) {
	            			if (!uLoc.getUser().equals(u)) {
	            				bounds.include(uLoc.getLatLng().toLatLng());
	            			}
	            		}
	                    bounds.include(destination);
	                    bounds.include(latLng);
	                            
//	                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//	                      mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//	                    } else {
//	                      mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//	                    }
	                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 10));
	                    Log.v("Joinusandroid", "MeetingMapFragment: move camera with bounds");
//	                }
//	            });
//	        }
//************
    	
    	
    	
    	if (sharingOwnLocation) {
    		Log.v("joinusandroid", "toggle ON -> sharing location");
    		
			UserLocation userLoc = new UserLocation();
	    	userLoc.setLatLng(new AnnotatedLatLng(latLng));
	    	userLoc.setUser(u);
	    	long timestamp = (new Date()).getTime();
	    	userLoc.setTimestamp(timestamp);
	    	joinusService.shareLocation(u.getPhone(), userLoc);
		} else {
			Log.v("joinusandroid", "toggle OFF -> nothing to share");
		}
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
		Log.v("Joinusandroid", "MyLocationButton clicked for MeetingMapFragment");
		
		// Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
		return false;
	}

	@Override
	public void onLocationShared() {
		Log.v("Joinusandroid", "Own location shared for MeetingMapFragment");
		Toast.makeText(context, "You just shared your location!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLocationsRetrieved(List<UserLocation> uLocs) {
		Log.v("joinusandroid", "Participant locations retrieved");
		
		locations = uLocs;
		
		long now, timestamp, millis;
		
		int i = 1;
		numParticipants = locations.size();
		for (UserLocation loc : locations) {
			//if (!loc.getUser().getPhone().equals(u.getPhone())) {
			
			now = new Date().getTime();
	    	timestamp = loc.getTimestamp();
	    	millis = now - timestamp;
// Android API 9+  ********    	
	    	String diff = String.format("%d min, %d sec ", 
	    		    TimeUnit.MILLISECONDS.toMinutes(millis),
	    		    TimeUnit.MILLISECONDS.toSeconds(millis) - 
	    		    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
	    	);
//************************
			
			if (!loc.getUser().equals(u)) {
				mMap.addMarker(new MarkerOptions()
					.position(loc.getLatLng().toLatLng())
    				.title(loc.getUser().getName())
    				.snippet(diff + "ago")
    				.icon(BitmapDescriptorFactory.defaultMarker(i * 360 / (numParticipants + 1))));
			i++;
			}
		}
	}

	/**
	 * @return the sharingOwnLocation
	 */
	public boolean isSharingOwnLocation() {
		return sharingOwnLocation;
	}

	/**
	 * @param sharingOwnLocation the sharingOwnLocation to set
	 */
	public void setSharingOwnLocation(boolean sharingOwnLocation) {
		this.sharingOwnLocation = sharingOwnLocation;
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