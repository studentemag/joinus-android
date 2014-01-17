package mag.joinus.model;

import java.sql.Date;

public class UserLocation extends Location{

	private int id;
	private Date date;
	private User user;
	
	public UserLocation(float latitude, float longitude, int id, Date date,
			User user) {
		super(latitude, longitude);
		this.id = id;
		this.date = date;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	public Date getDate() {
		return date;
	}
	public User getUser() {
		return user;
	}

}
