package mag.joinus.model;

import java.util.Date;

public class Meeting {
	String title;
	Date date;
	String place;
	
	public Meeting(String title, Date date, String place) {
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
