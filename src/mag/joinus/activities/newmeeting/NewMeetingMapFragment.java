package mag.joinus.activities.newmeeting;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.AnnotatedLatLng;
import mag.joinus.model.Meeting;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewMeetingMapFragment extends Fragment implements
		OnMapLongClickListener,
		LocationListener {
	
	private View rootView;
	private Context context;
	
    private GoogleMap mMap;
    private LocationManager mLocationManager;
        
    public NewMeetingMapFragment(){
    	super();
    	context = JoinusApplication.getInstance();
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_newmeeting_map,
				container, false);
		
		
		EditText meetingAddress = (EditText) rootView.findViewById(R.id.newmeeting_meeting_address);
		meetingAddress.setOnEditorActionListener(
				new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
							searchForLocation(v.getText().toString());
							return true;
						}
						return false;
		    			}
				});
		
		setUpMapIfNeeded();

		return rootView;
	}

	
	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.newmeeting_map))
                    .getMap();
            
            // Acquire a reference to the system Location Manager
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            		
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
    public void setUpMeetingLocation(String address, LatLng latLng){
    	
    	Meeting m = JoinusApplication.getInstance().getMeetingToCreate();
    	m.setAddress(address);
    	m.setLatLng(new AnnotatedLatLng(latLng));
    
    	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
    	((EditText) rootView.findViewById(R.id.newmeeting_meeting_address)).setText(address);
    	
    	mMap.clear(); 
    	mMap.addMarker(new MarkerOptions().position(latLng));
    }

    private void searchForLocation(String address){
    	new GetLocationTask(this,context).execute(address);
    }
    
    private void searchForAddress(Location l){
    	new GetAddressTask(this,context).execute(l);
    }

    private void setUpMap() {
        mMap.setOnMapLongClickListener(this);
        mMap.setMyLocationEnabled(true);

        // Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        // TODO lastKnownLocation is null and raises nullPointerException
        //android.location.Location lastKnownLocation = mLocationManager.getLastKnownLocation(Context.LOCATION_SERVICE);
        //LatLng ll = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 4.0f));
    }
    
	@Override
	public void onLocationChanged(Location location) {
		Log.v("newEventActivity","onLocationChanged"+location.toString());
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
		mLocationManager.removeUpdates(this);

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
	public void onMapLongClick(LatLng arg0) {
		Log.v("newEventActivity","onMapLongClick"+arg0.toString());
		Location l = new Location(LocationManager.NETWORK_PROVIDER);
		l.setLatitude(arg0.latitude);
		l.setLongitude(arg0.longitude);
		this.searchForAddress(l);

	}

}
