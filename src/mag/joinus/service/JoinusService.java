package mag.joinus.service;

import java.util.List;

import mag.joinus.activities.CreateMeetingListener;
import mag.joinus.model.Location;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;

public interface JoinusService {
	
	public Location getLocationFromAddress(String address);
	public Meeting createMeeting(
				CreateMeetingListener listener,
				String title, 
				long date, 
				Location location, 
				User mc, 
				List<String> phones);

	

}
