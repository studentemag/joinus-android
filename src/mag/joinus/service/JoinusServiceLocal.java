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
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class JoinusServiceLocal extends OrmLiteSqliteOpenHelper {
	
	private SQLiteDatabase db;
	private Dao<User, String> userDao = null;
	private Dao<Meeting, Integer> meetingDao = null;
//	private Dao<UserLocation,Integer> userLocationDao=null; 
	private Dao<AnnotatedLatLng,Integer> latLngDao = null;
	private Dao<Guest,Object> guestDao = null;
	private Dao<Participant,Object> participantDao = null;
	
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "joinus";
	
	public JoinusServiceLocal(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		context.deleteDatabase(DATABASE_NAME);
		Log.i("JoinusServiceLocalImpl", "in contructor");
		getWritableDatabase();
		try {
			userDao=getDao(User.class);
			meetingDao=getDao(Meeting.class);
			latLngDao=getDao(AnnotatedLatLng.class);
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
	
	public List<Meeting> getUpcomingEvents(String phone){
		List<Meeting> mList = new ArrayList<Meeting>();
		try {
			mList = meetingDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mList;
	}
	
	public void getUpcomingEventsWrite(List<Meeting>  meetings){
		try{
			for (Meeting m : meetings) {
				meetingDao.update(m);
				latLngDao.update(m.getLatLng());
				userDao.update(m.getMc());
				for (User u : m.getGuests()) {
					userDao.update(u);
					Guest g = new Guest(); 
					g.setMeeting_id(m.getId());
					g.setPhone(u.getPhone());
					guestDao.update(g);
				}
				for (User u : m.getParticipants()) {
					userDao.update(u);
					Participant p = new Participant();
					p.setMeeting_id(m.getId());
					p.setPhone(u.getPhone());
					participantDao.update(p);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
