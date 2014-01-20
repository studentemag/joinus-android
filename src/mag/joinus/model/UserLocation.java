package mag.joinus.model;


public class UserLocation extends Location{

	private int id;
	private long date;//TODO sul server si chiama timestamp
	private User user;
	
	public UserLocation(float latitude, float longitude, int id, long date,
			User user) {
		super(latitude, longitude);
		this.id = id;
		this.date = date;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	public long getDate() {
		return date;
	}
	public User getUser() {
		return user;
	}

}
