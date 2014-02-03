package mag.joinus.service.listeners;

import mag.joinus.model.User;

public interface GetUserListener {
	
	public void onUserRetrieved(User user);
	public void onUserRetrievedError();
}
