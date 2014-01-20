package mag.joinus.model;

import java.util.List;

import android.location.Location;


public class User {
	
	private int id;
	
	private List<Location> locations;

	private List<Meeting> meetingsAsGuest;

	private List<Meeting> meetingsAsMoc;

	private List<Meeting> meetingsAsParticipant;

	private String phone;

	private String userName;

	protected User() {}

	public User(String userName) {
		super();
		this.userName = userName;
	}

	public int getId() {
		return id;
	}
	
	public List<Location> getLocations() {
		return locations;
	}
	
	public List<Meeting> getMeetingsAsGuest() {
		return meetingsAsGuest;
	}
	
	public List<Meeting> getMeetingsAsMoc() {
		return meetingsAsMoc;
	}
	
	public List<Meeting> getMeetingsAsParticipant() {
		return meetingsAsParticipant;
	}
	
	public String getName() {
		return userName;
	}

	public String getPhone() {
		return phone;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setId(int id) {
		this.id = id;
	}


}
