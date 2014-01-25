package mag.joinus.activities.meeting;


import java.util.Date;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.service.JoinusServiceImpl;
import mag.joinus.service.listeners.FindMeetingListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
public class MeetingInfoFragment extends Fragment implements FindMeetingListener{
	
	/*
	 * Input parameters
	 */
	public static final String MEETING_ID = "meeting_id";
	
	/*
	 * Service
	 */
	JoinusServiceImpl joinusService;
	
	/*
	 * Significant ui elements
	 */
	TextView addressTextView;
	TextView dateTextView;
	TextView mcTextView;
	TextView participantsTextView;
	TextView guestsTextView;
	
	private Meeting m;
	private User u;
	
	public MeetingInfoFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_meeting_info, container, false);
		joinusService = JoinusApplication.getInstance().getService();
		joinusService.setFindMeetingListener(this);
		
		addressTextView = (TextView) rootView.findViewById(R.id.meeting_info_address);
		dateTextView = (TextView) rootView.findViewById(R.id.meeting_info_date);
		mcTextView = (TextView) rootView.findViewById(R.id.meeting_info_mc_content);
		participantsTextView = (TextView) rootView.findViewById(R.id.meeting_info_participants);
		guestsTextView = (TextView) rootView.findViewById(R.id.meeting_info_guests_content);
		
		/*int meetingId = getArguments().getInt(MEETING_ID);
		m = new Meeting();
		m.setId(meetingId);*/
		m = JoinusApplication.getInstance().getMeeting();
		u = JoinusApplication.getInstance().getUser();
		
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.v("MeetingInfoFrag.onStart", m.getId() + "");
		onMeetingFound(m);
		//m = new Meeting();
	}

	@Override
	public void onMeetingFound(Meeting m) {
		// TODO settare il titolo sulla barra in alto
		
		Date d = new Date(m.getDate());
		
		addressTextView.setText(m.getAddress());
		dateTextView.setText(d.toString());
		mcTextView.setText(m.getMc().toString());
		
		String participants = "";
		for (int i = 0; i < m.getParticipants().size(); i++)
			participants += m.getParticipants().get(i).getName() + " ";
				
		participantsTextView.setText(participants);
		
		String guests = "";
		for (int i = 0; i < m.getGuests().size(); i++)
			guests += m.getGuests().get(i).getName() + " ";
				
		guestsTextView.setText(guests);
	}
	
	public void accept(View view) {
		Log.v("joinUsAndroid", "accept called");

		Toast.makeText(JoinusApplication.getInstance().getApplicationContext(), R.string.meeting_info_server_request, Toast.LENGTH_LONG).show();
		
		joinusService.acceptInvitationTo(m.getId(), u);
		
		Toast.makeText(JoinusApplication.getInstance().getApplicationContext(), R.string.meeting_info_server_response, Toast.LENGTH_LONG).show();
	}
}