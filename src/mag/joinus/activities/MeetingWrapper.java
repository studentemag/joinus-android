package mag.joinus.activities;

import java.util.Observable;

import mag.joinus.model.Meeting;


public class MeetingWrapper extends Observable {

	public MeetingWrapper() {
		meet = null;
	}
	
	public void setMeeting(Meeting m) {
		meet = m;
		this.setChanged();
		this.notifyObservers();
	}
	
	public Meeting getMeeting() {
		return meet;
	}

	private Meeting meet;
}
