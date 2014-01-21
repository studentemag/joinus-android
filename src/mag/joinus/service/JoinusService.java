package mag.joinus.service;

import java.util.List;

import mag.joinus.activities.CreateMeetingListener;
import mag.joinus.activities.GetMeetingListListener;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface JoinusService {
	
	public Location getLocationFromAddress(String address);
	
	public Meeting createMeeting(
				CreateMeetingListener listener,
				String title, 
				long timestamp, 
				LatLng location, 
				User mc, 
				List<String> phones);

	public List<Meeting> getUpcomingEvents(int userId);
	
	public void setGetMeetingListListener(GetMeetingListListener listener);

}

