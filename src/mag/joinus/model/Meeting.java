package mag.joinus.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public Meeting(JSONObject j){
		try {
			if (!j.isNull("id"))
				this.id=j.getInt("id");
			if (!j.isNull("address"))
				this.address=j.getString("address");
			if (!j.isNull("date"))
				this.date=j.getLong("date");
			if (!j.isNull("title"))
				this.title=j.getString("title");
			if (!j.isNull("latLng"))
				this.latLng=new AnnotatedLatLng(j.getJSONObject("latLng"));
			if (!j.isNull("mc"))
				this.mc=new User(j.getJSONObject("latLng"));
			if (!j.isNull("guests")) {
				this.guests = new ArrayList<User>();
				JSONArray guests = j.getJSONArray("guests");
				for (int i=0; i<guests.length(); i++)
					this.guests.add(new User(guests.getJSONObject(i)));
			}
			if (!j.isNull("participants")) {
				this.participants = new ArrayList<User>();
				JSONArray participants = j.getJSONArray("participants");
				for (int i=0; i<participants.length(); i++)
					this.participants.add(new User(participants.getJSONObject(i)));
			}	
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject toJson(){
		JSONObject meetingj = new JSONObject();
		try {
			meetingj.put("id", this.id);
			meetingj.put("title", this.title);
			meetingj.put("address", this.address);
			meetingj.put("date", this.date);
			if (latLng!=null)
				meetingj.put("latLng", latLng.toJson());
			if (mc!=null)
				meetingj.put("mc", mc.toJson());
			if (guests!=null){
				meetingj.put("guests", new JSONArray());
				for(User guest : guests)
					meetingj.accumulate("guests",guest.toJson());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return meetingj;
		
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
