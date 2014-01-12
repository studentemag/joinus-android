package meg.joinus.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JoinUs {
	String userid;
	List <Event> events;
	
	public JoinUs(){
		Event e1 = new Event("Festa di Gianni", new Date(), "Moiano");
		Event e2 = new Event("Cena con Filippo", new Date(), "Sapori di Principe");
		Event e3 = new Event("Incontro gifra", new Date(), "Saletta San Francesco");
		events = new ArrayList<Event>(); 
		events.add(e1);events.add(e2);events.add(e3);
	}
	
	public void signIn(String userid){
		this.userid=userid;
	}
	
	public List<Event> getUpcomingEvents(){
		return events;
	}
	

}
