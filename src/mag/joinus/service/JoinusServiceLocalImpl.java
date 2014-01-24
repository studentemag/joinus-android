package mag.joinus.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mag.joinus.model.AnnotatedLatLng;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.model.UserLocation;
import mag.joinus.model.db.Guest;
import mag.joinus.model.db.Participant;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class JoinusServiceLocalImpl extends OrmLiteSqliteOpenHelper implements JoinusService {
	
	private SQLiteDatabase db;
	private Dao<User, Integer> userDao = null;
	private Dao<Meeting, Integer> meetingDao = null;
	private Dao<UserLocation,Integer> userLocationDao=null; 
	private Dao<AnnotatedLatLng,Integer> latLngDao = null;
	private Dao<Guest,Object> guestDao = null;
	private Dao<Participant,Object> participantDao = null;
	
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "joinus";
	
	public JoinusServiceLocalImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		context.deleteDatabase(DATABASE_NAME);
		Log.i("JoinusServiceLocalImpl", "in contructor");
		getWritableDatabase();
		try {
			userDao=getDao(User.class);
			meetingDao=getDao(Meeting.class);
			latLngDao=getDao(AnnotatedLatLng.class);
			userLocationDao = getDao(UserLocation.class);
			guestDao=getDao(Guest.class);
			participantDao=getDao(Participant.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		Log.i("JoinusServiceLocalImpl", "onCreate");
		try{
	        TableUtils.createTable(connectionSource, User.class);
	        TableUtils.createTable(connectionSource, Meeting.class);
	        TableUtils.createTable(connectionSource, AnnotatedLatLng.class);
	        TableUtils.createTable(connectionSource, UserLocation.class);
	        TableUtils.createTable(connectionSource, Guest.class); 
	        TableUtils.createTable(connectionSource, Participant.class); 
		}
		catch (SQLException e) {
			e.printStackTrace();	
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		Log.i("JoinusServiceLocalImpl", "onUpgrade");
		try{
			TableUtils.dropTable(connectionSource, User.class, true);
	        TableUtils.dropTable(connectionSource, Meeting.class, true);
	        TableUtils.dropTable(connectionSource, AnnotatedLatLng.class, true);
	        TableUtils.dropTable(connectionSource, UserLocation.class, true);
	        TableUtils.dropTable(connectionSource, Guest.class, true); 
	        TableUtils.dropTable(connectionSource, Participant.class, true); 
		}
		catch(SQLException e){
			
		}
		
		this.onCreate(arg0,arg1);
	}
	
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		try {
			userDao.createIfNotExists(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Location getLocationFromAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getUpcomingEvents(String phone) {
		List<Meeting> meetings = new ArrayList<Meeting>();
		try {
			List<Participant> meetingsAsP= participantDao.queryForEq("phone", phone);
			for (Participant p : meetingsAsP)
				meetings.add(meetingDao.queryForId(p.getMeeting_id()));
			List<Guest> meetingsAsG= guestDao.queryForEq("user_id", phone);
			for (Guest g : meetingsAsG)
				meetings.add(meetingDao.queryForId(g.getMeeting_id()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return meetings;
	}

	@Override
	public Meeting createMeeting(Meeting m) {
		// TODO Auto-generated method stub
		return null;

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
