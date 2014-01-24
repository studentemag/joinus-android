package mag.joinus.model;

import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {
	@DatabaseField
	private String name;
	
	@DatabaseField (id=true)
	private String phone;
	
	//JSONIgnore
	@ForeignCollectionField
	private Collection<UserLocation> locations;
	
	//JSONIgnore
	@ForeignCollectionField
	private Collection<Meeting> meetingsAsMc;
	
	//JSONIgnore
	private List<Meeting> meetingsAsGuest;
	
	//JSONIgnore
	private List<Meeting> meetingsAsParticipant;

	public User() {}
	
	public User(JSONObject j){
		try {
			if (!j.isNull("name"))
				this.name=j.getString("name");	
			if (!j.isNull("phone"))
				this.phone=j.getString("phone");	
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
	
	public JSONObject toJson(){
		JSONObject userj = new JSONObject();
		try{
			userj.put("phone", this.phone);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userj;
	}
}
