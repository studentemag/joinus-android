package mag.joinus.model;

import java.util.List;

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
	
	@Override
	public String toString(){
		String string = this.title + " " + this.address + " " +this.date+" "+this.guests.toString()+" "+this.latLng.toString();
		return string;
	}
	
	public boolean validateForCreation(){
		boolean valid = true;
		if (address==null || address.isEmpty())
			valid=false;
		if (date < 1390502294000L)
			valid=false;
		if (latLng==null)
			valid=false;
		if (title==null || title.isEmpty())
			valid = false;
		if (guests==null || guests.isEmpty())
			valid = false;
		if (mc==null)
			valid=false;
		
		return valid;
	}

}
