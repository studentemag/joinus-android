package meg.joinus.model;

import java.util.Date;

public class Event {
	String title;
	Date date;
	String place;
	
	public Event(String title, Date date, String place) {
		super();
		this.title = title;
		this.date = date;
		this.place = place;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

	public String getPlace() {
		return place;
	}
	
	
}
