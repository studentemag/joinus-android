package mag.joinus.service;

import java.util.Date;
import java.util.List;

import mag.joinus.model.Location;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;

public class JoinusServiceImpl implements JoinusService{

	private static JoinusService service = null;
	
	public static JoinusService getService(){
		if (service==null)
			service = new JoinusServiceImpl();
		return service;
	}
	

	@Override
	public Location getLocationFromAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting createMeeting(String title, Date date, Location location,
			User mc, List<String> phones) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
