package mag.joinus.service;

import java.util.List;

import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.model.UserLocation;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.location.Location;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class JoinusServiceLocalImpl extends OrmLiteSqliteOpenHelper implements JoinusService {
	
	
	public JoinusServiceLocalImpl(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}

	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "joinus";
    	
	
    //public JoinusServiceLocalImpl(Context context) {
    //    super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
    //}
	
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public User login(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocationFromAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getUpcomingEvents(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMeeting(Meeting m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Meeting getMeeting(int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting acceptInvitationTo(int userId, int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting denyInvitationTo(int userId, int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLocation> getLastKnownParticipantsLocations(int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendLocation(int userId, Location l) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findUserByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting addParticipantsToMeeting(List<User> users, int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}




}
