package mag.joinus.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_locations")
public class UserLocation {

	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "user_phone")
	private User user;

	@DatabaseField
	long timestamp;
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "latlng_id")
	private AnnotatedLatLng latLng;

	
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
