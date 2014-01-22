package mag.joinus.model;


import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_locations")
public class UserLocation {

	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "user_id")
	private User user;

	@DatabaseField
	long timestamp;
	
	private LatLng latLng;	
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "latlng_id")
	private AnnotatedLatLng latLng2;

	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public AnnotatedLatLng getLatLng2() {
		return latLng2;
	}

	public void setLatLng2(AnnotatedLatLng latLng2) {
		this.latLng2 = latLng2;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
