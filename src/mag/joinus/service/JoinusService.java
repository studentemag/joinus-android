package mag.joinus.service;

import java.util.Date;
import java.util.List;

import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import android.location.Location;

public interface JoinusService {
	
	public Location getLocationFromAddress(String address);
	public Meeting createMeeting(
				String title, 
				Date date, 
				Location location, 
				User mc, 
				List<String> phones);

	

}
