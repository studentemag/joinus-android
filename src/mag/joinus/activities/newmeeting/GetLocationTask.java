package mag.joinus.activities.newmeeting;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

class GetLocationTask extends AsyncTask<String, Void, Location> {
	/**
	 * 
	 */
	private final NewMeetingMapFragment newMeetingMapFragment;
	Context mContext;
	String stringAddress;
	
	public GetLocationTask(NewMeetingMapFragment newMeetingMapFragment, Context context) {
		super();
		this.newMeetingMapFragment = newMeetingMapFragment;
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
			String errorString = "Illegal argument: " + stringAddress +
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
	protected void onPostExecute(Location l) {
		LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
		this.newMeetingMapFragment.setUpMeetingLocation(stringAddress, latLng);
	}
}