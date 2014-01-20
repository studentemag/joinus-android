package mag.joinus.service;

import java.util.List;

import mag.joinus.model.Location;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import android.support.v4.app.FragmentActivity;

public interface JoinusService {
	
	public Location getLocationFromAddress(String address);
	public Meeting createMeeting(FragmentActivity obs,
				String title, 
				long date, 
				Location location, 
				User mc, 
				List<String> phones);

	

}
