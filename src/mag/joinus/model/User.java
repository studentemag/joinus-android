package mag.joinus.model;

import java.util.Collection;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {
	
	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField
	private String name;
	
	@ForeignCollectionField
	private Collection<UserLocation> locations;

	@DatabaseField
	private String phone;
	
	@ForeignCollectionField
	private Collection<Meeting> meetingsAsMc;
	
	private List<Meeting> meetingsAsGuest;
	
	private List<Meeting> meetingsAsParticipant;

	public User() {}

	public int getId() {
		return id;
	}
	
	public Collection<UserLocation> getLocations() {
		return locations;
	}
	
	public List<Meeting> getMeetingsAsGuest() {
		return meetingsAsGuest;
	}
	
	public Collection<Meeting> getMeetingsAsMc() {
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

	public void setLocations(List<UserLocation> locations) {
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
