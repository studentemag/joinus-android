package meg.joinus.model;

import java.util.List;

public class JoinUsServiceUtil {
	private static JoinUs service = null;
	
	public static void signIn(String userid){
		getService().signIn(userid);
	}
	
	public static List<Event> getUpcomingEvents(){
		return getService().getUpcomingEvents();
	}
	
	private static JoinUs getService(){
		if (service==null)
			service = new JoinUs();
		return service;
	}
	
	
}
