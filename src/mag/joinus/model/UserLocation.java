package mag.joinus.model;

import android.location.Location;

public class UserLocation extends Location {

	private User user;
	
	public UserLocation(Location l) {
		super(l);
	}

	public UserLocation(String provider) {
		super(provider);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
