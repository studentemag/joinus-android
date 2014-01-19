package mag.joinus.model;

public class Meeting {
	String title;
	long date;
	String place;

	public Meeting() {
	
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTitle() {
		return title;
	}

	public long getDate() {
		return date;
	}

	public String getPlace() {
		return place;
	}
	
	
}
