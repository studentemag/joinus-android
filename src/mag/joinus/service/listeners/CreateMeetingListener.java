package mag.joinus.service.listeners;

import mag.joinus.model.Meeting;

public interface CreateMeetingListener {
	public void onMeetingCreated(Meeting meet);
}
