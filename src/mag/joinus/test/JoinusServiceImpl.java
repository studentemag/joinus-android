package mag.joinus.test;

import java.util.ArrayList;
import java.util.List;

import mag.joinus.model.AnnotatedLatLng;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;

import com.google.android.gms.maps.model.LatLng;

public class JoinusServiceImpl {
	public Meeting findMeetingStub(int meetingId) {
		User mario = new User();
		mario.setName("Mario");
		User luca = new User();
		luca.setName("Luca");
		User fabio = new User();
		fabio.setName("Fabio");
		User giuseppe = new User();
		giuseppe.setName("Giuseppe");

		List<User> participants = new ArrayList<User>();
		participants.add(giuseppe);
		participants.add(luca);
		participants.add(mario);

		List<User> guests = new ArrayList<User>();
		guests.add(giuseppe);
		guests.add(fabio);
		guests.add(luca);
		guests.add(mario);

		LatLng l = new LatLng(0, 0);

		Meeting m = new Meeting();

		m.setAddress("via congo d'oro");
		m.setDate(10321321);
		m.setGuests(guests);
		m.setId(meetingId);
		m.setLatLng(new AnnotatedLatLng(l));
		m.setMc(luca);
		m.setParticipants(participants);
		m.setTitle("festa di Luca");
		return m;
	}

}
