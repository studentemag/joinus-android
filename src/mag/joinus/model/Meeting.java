package mag.joinus.model;

import java.util.List;

public class Meeting {
	//TODO siamo sicuri serva l'id nel lato client?
	private int id;
	private String address;
	private long date;
	private List<User> guests;
	//TODO potrebbe aver senso il tipo Location
	private float latitude;
	private float longitude;
	private User mc;
	private List<User> participants;
	private String title;
	
	public Meeting() {}
	
	public String getAddress() {
		return address;
	}
	
	public long getDate() {
		return date;
	}
	
	public List<User> getGuests() {
		return guests;
	}
	
	public int getId() {
		return id;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public User getMc() {
		return mc;
	}
	
	public List<User> getParticipants() {
		return participants;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public void setGuests(List<User> guests) {
		this.guests = guests;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLatitude(float latitude) {
		this.latitude=latitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public void setMc(User mc) {
		this.mc = mc;
	}
	
	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
