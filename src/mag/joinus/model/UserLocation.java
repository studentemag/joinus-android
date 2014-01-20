package mag.joinus.model;


public class UserLocation extends Location {

	private int id;
	private long timestamp;
	private User user;
	
	public UserLocation(float latitude, float longitude, int id, long timestamp,
			User user) {
		super(latitude, longitude);
		this.id = id;
		this.timestamp = timestamp;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public User getUser() {
		return user;
	}

}
