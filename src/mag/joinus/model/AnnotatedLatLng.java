package mag.joinus.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "latlngs")
public class AnnotatedLatLng {
	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField
	private double latitude;
	
	@DatabaseField
	private double longitude;

	public AnnotatedLatLng() {}
	
	public AnnotatedLatLng(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public AnnotatedLatLng(LatLng latlng) {
		super();
		this.latitude = latlng.latitude;
		this.longitude = latlng.longitude;
	}
	
	public AnnotatedLatLng(JSONObject j) {
		try {
			if (!j.isNull("id"))
				this.id=j.getInt("id");
			if (!j.isNull("latitude"))
				this.latitude=j.getDouble("latitude");
			if (!j.isNull("longitude"))
				this.longitude=j.getDouble("longitude");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public LatLng toLatLng(){
		return new LatLng(latitude,longitude);
	}
	
	@Override
	public String toString(){
		return "\"latitude\" " + latitude + ", \"" + longitude + "\" ";
	}
	
	public JSONObject toJson(){
		JSONObject latLngj = new JSONObject();
		try {
			latLngj.put("id", this.id);
			latLngj.put("latitude", this.latitude);
			latLngj.put("longitude", this.longitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return latLngj;
	}
	
	
	
}

