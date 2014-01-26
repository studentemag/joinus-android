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
	
	@DatabaseField (id = true)
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

	public User() {
		
	}
	
	public User(JSONObject j) {
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
	
	public JSONObject toJson() {
		JSONObject userj = new JSONObject();
		try {
			userj.put("name", this.name);
			userj.put("phone", this.phone);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userj;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((locations == null) ? 0 : locations.hashCode());
		result = prime * result
				+ ((meetingsAsGuest == null) ? 0 : meetingsAsGuest.hashCode());
		result = prime * result
				+ ((meetingsAsMc == null) ? 0 : meetingsAsMc.hashCode());
		result = prime
				* result
				+ ((meetingsAsParticipant == null) ? 0 : meetingsAsParticipant
						.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	//Actually based only on the field "phone"
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		
		/*if (locations == null) {
			if (other.locations != null) {
				return false;
			}
		} else if (!locations.equals(other.locations)) {
			return false;
		}
		if (meetingsAsGuest == null) {
			if (other.meetingsAsGuest != null) {
				return false;
			}
		} else if (!meetingsAsGuest.equals(other.meetingsAsGuest)) {
			return false;
		}
		if (meetingsAsMc == null) {
			if (other.meetingsAsMc != null) {
				return false;
			}
		} else if (!meetingsAsMc.equals(other.meetingsAsMc)) {
			return false;
		}
		if (meetingsAsParticipant == null) {
			if (other.meetingsAsParticipant != null) {
				return false;
			}
		} else if (!meetingsAsParticipant.equals(other.meetingsAsParticipant)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}*/
		
		if (phone == null) {
			if (other.phone != null) {
				return false;
			}
		} else if (!phone.equals(other.phone)) {
			return false;
		}
		return true;
	}
}
