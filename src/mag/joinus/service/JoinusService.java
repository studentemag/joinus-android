package mag.joinus.service;

import java.util.List;

import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.model.UserLocation;
import android.location.Location;

public interface JoinusService {
	
	public Location getLocationFromAddress(String address);
	
	public List<Meeting> getUpcomingEvents(int userId);

	public void createMeeting(Meeting m);
	
	public Meeting getMeeting(int meetingId); // Move to service impl


	public Meeting acceptInvitationTo(int userId, int meetingId);
	public Meeting denyInvitationTo(int userId, int meetingId);
	
	public List<UserLocation> getLastKnownParticipantsLocations(int meetingId);
	
	public void sendLocation(int userId, Location l);
	
	public User findUserByPhoneNumber(String phoneNumber);

	public Meeting addParticipantsToMeeting(List<User> users, int meetingId);
}

