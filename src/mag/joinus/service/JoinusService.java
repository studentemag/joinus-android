package mag.joinus.service;

import java.util.List;

import mag.joinus.activities.CreateMeetingListener;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import android.location.Location;

public interface JoinusService {
	
	public Location getLocationFromAddress(String address);
	public Meeting createMeeting(
				CreateMeetingListener listener,
				String title, 
				long timestamp, 
				Location location, 
				User mc, 
				List<String> phones);

	

}

