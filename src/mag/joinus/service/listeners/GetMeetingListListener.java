package mag.joinus.service.listeners;

import java.util.List;

import mag.joinus.model.Meeting;

public interface GetMeetingListListener {

	public void onMeetingListRetrieved(List<Meeting> mList);
}