package mag.joinus.model;

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
	
	
	
}
