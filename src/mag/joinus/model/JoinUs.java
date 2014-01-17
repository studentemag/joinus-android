package mag.joinus.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JoinUs {
	String userid;
	List <Meeting> events;
	
	public JoinUs(){
		Meeting e1 = new Meeting("Festa di Gianni", new Date(), "Moiano");
		Meeting e2 = new Meeting("Cena con Filippo", new Date(), "Sapori di Principe");
		Meeting e3 = new Meeting("Incontro gifra", new Date(), "Saletta San Francesco");
		events = new ArrayList<Meeting>(); 
		events.add(e1);events.add(e2);events.add(e3);
	}
	
	public void signIn(String userid){
		this.userid=userid;
	}
	
	public List<Meeting> getUpcomingEvents(){
		return events;
	}
	

}
