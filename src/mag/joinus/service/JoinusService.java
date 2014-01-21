package mag.joinus.service;

import java.util.List;

import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.model.UserLocation;
import mag.joinus.service.listeners.CreateMeetingListener;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface JoinusService {
	
	public Location getLocationFromAddress(String address);
	public Meeting createMeeting(
				CreateMeetingListener listener,
				String title, 
				long timestamp, 
				LatLng location, 
				User mc, 
				List<String> phones);
	
	public Meeting createMeeting(Meeting m);
	
	public Meeting getMeeting(int meetingId);

	public Meeting acceptInvitationTo(int meetingId);
	public Meeting denyInvitationTo(int meetingId);
	
	public List<UserLocation> getLastKnownLocations(int meetingId);
	
	public void sendLocation(Location l);
	
	public User findUserByPhoneNumber(String phoneNumber);

	public Meeting addParticipantsToMeeting(List<User> users, int meetingId);
}

