package mag.joinus.model;

import java.util.List;

import android.location.Location;


public class User {
	
	private int id;
	
	private String name;
	
	private List<Location> locations;

	private List<Meeting> meetingsAsGuest;

	private List<Meeting> meetingsAsMc;

	private List<Meeting> meetingsAsParticipant;

	private String phone;

	public User() {}

	public int getId() {
		return id;
	}
	
	public List<Location> getLocations() {
		return locations;
	}
	
	public List<Meeting> getMeetingsAsGuest() {
		return meetingsAsGuest;
	}
	
	public List<Meeting> getMeetingsAsMc() {
		return meetingsAsMc;
	}
	
	public List<Meeting> getMeetingsAsParticipant() {
		return meetingsAsParticipant;
	}
	
	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public void setMeetingsAsGuest(List<Meeting> meetingsAsGuest) {
		this.meetingsAsGuest = meetingsAsGuest;
	}

	public void setMeetingsAsMc(List<Meeting> meetingsAsMc) {
		this.meetingsAsMc = meetingsAsMc;
	}

	public void setMeetingsAsParticipant(List<Meeting> meetingsAsParticipant) {
		this.meetingsAsParticipant = meetingsAsParticipant;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setName(String name) {
		this.name = name;
	}
}
