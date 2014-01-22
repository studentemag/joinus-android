package mag.joinus.model;

import java.util.List;


import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "meetings")
public class Meeting {
	
	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField
	private String address;
	
	@DatabaseField
	private long date;
	
	@DatabaseField
	private String title;
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "latlng_id")
	private AnnotatedLatLng latLng;

	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "mc_id")
	private User mc;
	
	private List<User> guests;
	private List<User> participants;
	
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
	
	public User getMc() {
		return mc;
	}
	
	public List<User> getParticipants() {
		return participants;
	}
	
	public String getTitle() {
		return title;
	}
	
	public AnnotatedLatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(AnnotatedLatLng latLng) {
		this.latLng = latLng;
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
