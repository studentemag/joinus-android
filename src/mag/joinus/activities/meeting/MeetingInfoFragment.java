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
import android.widget.Button;
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
	//public static final String MEETING_ID = "meeting_id";
	
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
	
	Button acceptButton;
	Button denyButton;
	
	private Meeting m;
	private User u;
	
	public MeetingInfoFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Registering as InfoFragment for MeetingActivity
		JoinusApplication.getInstance().setInfoFragment(this);
		
		View rootView = inflater.inflate(R.layout.fragment_meeting_info, container, false);
		joinusService = JoinusApplication.getInstance().getService();
		joinusService.setFindMeetingListener(this);
		
		addressTextView = (TextView) rootView.findViewById(R.id.meeting_info_address);
		dateTextView = (TextView) rootView.findViewById(R.id.meeting_info_date);
		mcTextView = (TextView) rootView.findViewById(R.id.meeting_info_mc_content);
		participantsTextView = (TextView) rootView.findViewById(R.id.meeting_info_participants);
		guestsTextView = (TextView) rootView.findViewById(R.id.meeting_info_guests_content);
		
		acceptButton = (Button) rootView.findViewById(R.id.meeting_info_accept_button);
		denyButton = (Button) rootView.findViewById(R.id.meeting_info_deny_button);
		
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
		JoinusApplication.getInstance().setMeeting(m);		
		
		acceptButton.setVisibility(View.GONE);
		denyButton.setVisibility(View.GONE);
		
		addressTextView.setText(m.getAddress());
		Date d = new Date(m.getDate());
		dateTextView.setText(d.toString());
		mcTextView.setText(m.getMc().getName());
		
		String participants = "";
		for (int i = 0; i < m.getParticipants().size(); i++) {
			if (i > 0)
				participants += ", ";
			
			participants += m.getParticipants().get(i).getName();
		}
				
		participantsTextView.setText(participants);
		
		String guests = "";
		for (int i = 0; i < m.getGuests().size(); i++)
			guests += m.getGuests().get(i).getName() + " ";
				
		guestsTextView.setText(guests);
		
//		//if (u.getPhone().equals(m.getMc().getPhone())) {
//		if (u.equals(m.getMc())) {
//			acceptButton.setVisibility(View.GONE);
//			denyButton.setVisibility(View.GONE);
//		}
		
		/*List<User> pList = m.getParticipants();
		boolean found = false;
		for (User p : pList) {
			if (p.getPhone().equals(u.getPhone()))
				found = true;
		}*/
		
		if (m.getParticipants().contains(u))
			denyButton.setVisibility(View.VISIBLE);
		
		if (m.getGuests().contains(u)){
			denyButton.setVisibility(View.VISIBLE);
			acceptButton.setVisibility(View.VISIBLE);
		}
			
	}
	
	public void accept(View view) {
		Log.v("joinUsAndroid", "accept called");

		Toast.makeText(JoinusApplication.getInstance().getApplicationContext(), R.string.meeting_info_server_request, Toast.LENGTH_LONG).show();
		
		joinusService.setFindMeetingListener(this);
		joinusService.acceptInvitationTo(m.getId(), u);
		
		Toast.makeText(JoinusApplication.getInstance().getApplicationContext(), R.string.meeting_info_server_response, Toast.LENGTH_LONG).show();
	}
	
	public void deny(View view) {
		Log.v("joinUsAndroid", "deny called");

		Toast.makeText(JoinusApplication.getInstance().getApplicationContext(), R.string.meeting_info_server_request, Toast.LENGTH_LONG).show();
		
		joinusService.setFindMeetingListener(this);
		joinusService.denyInvitationTo(m.getId(), u);
		
		Toast.makeText(JoinusApplication.getInstance().getApplicationContext(), R.string.meeting_info_server_response, Toast.LENGTH_LONG).show();
	}
}