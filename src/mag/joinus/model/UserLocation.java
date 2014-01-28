package mag.joinus.model;


import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_locations")
public class UserLocation {

	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "user_phone")
	private User user;

	@DatabaseField
	long timestamp;
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "latlng_id")
	private AnnotatedLatLng latLng;

	public UserLocation() {
		
	}
	
	public UserLocation(JSONObject j) {
		try {
			if (!j.isNull("id"))
				this.id = j.getInt("id");
			if (!j.isNull("timestamp"))
				this.timestamp = j.getLong("timestamp");
			if (!j.isNull("latLng"))
				this.latLng = new AnnotatedLatLng(j.getJSONObject("latLng"));
			if (!j.isNull("user"))
				this.user = new User(j.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject toJson() {
		JSONObject uLocj = new JSONObject();
		try {
			uLocj.put("id", this.id);
			if (user != null)
				uLocj.put("user", user.toJson());
			uLocj.put("timestamp", this.timestamp);
			if (latLng != null)
				uLocj.put("latLng", latLng.toJson());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return uLocj;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public AnnotatedLatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(AnnotatedLatLng latLng) {
		this.latLng = latLng;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
