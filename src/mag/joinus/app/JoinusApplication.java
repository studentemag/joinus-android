package mag.joinus.app;

import mag.joinus.activities.meeting.MeetingInfoFragment;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.service.JoinusServiceImpl;
import android.app.Application;

public class JoinusApplication extends Application {
    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static JoinusApplication sInstance;
    
    private JoinusServiceImpl service;
    
    // Logged user
    private User user;

    // Actual meeting (e.g. in Meeting Info)
	private Meeting meeting;
	
	// Info fragment
	private MeetingInfoFragment infoFragment;
    
	// Meeting to create
    private Meeting meetingToCreate;
    private long meetingToCreateDate;
    private long meetingToCreateTime;
    
	@Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        sInstance = this;
        service = new JoinusServiceImpl(this);
        meetingToCreate=new Meeting();
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized JoinusApplication getInstance() {
        return sInstance;
    }

    /**
	 * @return the service
	 */
	public JoinusServiceImpl getService() {		
		return service;
	}
	
	public Meeting getMeetingToCreate() {
		return meetingToCreate;
	}

	public void setMeetingToCreate(Meeting meetingToCreate) {
		this.meetingToCreate = meetingToCreate;
	}
	
	public long getMeetingToCreateDate() {
		return meetingToCreateDate;
	}

	public void setMeetingToCreateDate(long meetingToCreateDate) {
		this.meetingToCreateDate = meetingToCreateDate;
	}

	public long getMeetingToCreateTime() {
		return meetingToCreateTime;
	}

	public void setMeetingToCreateTime(long meetingToCreateTime) {
		this.meetingToCreateTime = meetingToCreateTime;
	}
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the meeting
	 */
	public Meeting getMeeting() {
		return meeting;
	}

	/**
	 * @param meeting the meeting to set
	 */
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	/**
	 * @return the infoFragment
	 */
	public MeetingInfoFragment getInfoFragment() {
		return infoFragment;
	}

	/**
	 * @param infoFragment the infoFragment to set
	 */
	public void setInfoFragment(MeetingInfoFragment infoFragment) {
		this.infoFragment = infoFragment;
	}
}

