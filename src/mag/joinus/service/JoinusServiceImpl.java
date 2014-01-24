package mag.joinus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mag.joinus.app.JoinusApplication;
import mag.joinus.model.AnnotatedLatLng;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.model.UserLocation;
import mag.joinus.service.listeners.CreateMeetingListener;
import mag.joinus.service.listeners.FindMeetingListener;
import mag.joinus.service.listeners.GetMeetingListListener;
import mag.joinus.service.listeners.GetUserListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class JoinusServiceImpl implements JoinusService {
	final String BASE_URL = "http://93.65.216.110:8080";

	private final String host = "http://151.24.52.86:8080";
	
	private JoinusServiceLocalImpl joinusServiceLocalImpl;
	
	private CreateMeetingListener createMeetingListener;
	private FindMeetingListener findMeetingListener;
	private GetMeetingListListener getMeetingListListener;
	private GetUserListener getUserListener;

	public JoinusServiceImpl(Context context){
		joinusServiceLocalImpl = OpenHelperManager.getHelper(context, JoinusServiceLocalImpl.class);
	}
	/**
	 * Log or request TAG
	 */
	public static final String TAG = "VolleyPatterns";
	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueue;

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	private RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(JoinusApplication
					.getInstance().getApplicationContext());
		}

		return mRequestQueue;
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		Log.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified then
	 * it is used else Default TAG is used.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		Log.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	
	@Override
	public Meeting acceptInvitationTo(int userId, int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting addParticipantsToMeeting(List<User> users, int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting createMeeting(Meeting m) {
		final String URL = host+"/events";

		JSONObject body = m.toJson();
		Log.v("JoinusServiceImpl::createMeeting", 
				"requestBody: " +body.toString());
		
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL,
				body, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.v("JoinusServiceImpl::createMeeting", 
								"responseBody: " +response.toString());

						Meeting m = new Meeting(response);
						
						createMeetingListener.onMeetingCreated(m);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Error: ", error.getMessage());
					}
				});

		// add the request object to the queue to be executed
		addToRequestQueue(req);
		
		return null;
	}

	@Override
	public Meeting denyInvitationTo(int userId, int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	private Meeting findMeetingStub(int meetingId) {
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

	@Override
	public User findUserByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public FindMeetingListener getFindMeetingListener() {
		return findMeetingListener;
	}

	@Override
	public List<UserLocation> getLastKnownParticipantsLocations(int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocationFromAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int meetingId) {
		final String URL = BASE_URL + "/meetings/";
		
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL,
				null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.v("joinusandroid", response.toString());

				Meeting m = new Meeting();
				try {
					m.setId(response.getInt("id"));
					m.setTitle(response.getString("title"));
					m.setDate(response.getLong("date"));
					m.setAddress(response.getString("address"));

					createMeetingListener.onMeetingCreated(m);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Error: ", error.getMessage());
			}
		});

		// add the request object to the queue to be executed
		addToRequestQueue(req);

		
		Meeting m = this.findMeetingStub(meetingId);
		findMeetingListener.onMeetingFound(m);
		return m;
	}

	@Override
	public List<Meeting> getUpcomingEvents(String phone) {
		final String URL = host+"/users/" + phone + "/events";

		// Default method is GET
		JsonArrayRequest req = new JsonArrayRequest(URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.v("joinusandroid", response.toString());
						List<Meeting> mList = new ArrayList<Meeting>();
						try {
							if (response != null) {
								for (int i = 0; i < response.length(); i++) {
									Meeting m = new Meeting(response.getJSONObject(i));
									mList.add(m);
								}
							}
							getMeetingListListener.onMeetingListRetrieved(mList);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Error: ", error.getMessage());
					}
				});

		// add the request object to the queue to be executed
		addToRequestQueue(req);

		List<Meeting> mList = new ArrayList<Meeting>();
		Meeting m = new Meeting();
		m.setTitle("il mio compleanno");
		LatLng l = new LatLng(112,345);
		m.setLatLng(new AnnotatedLatLng(l));
		mList.add(m);

		return mList;
	}

	@Override
	public User login(User user) {
		String name = user.getName();
		String phone = user.getPhone();
		
		final String URL = host+"/users";

		// Post params to be sent to the server
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("phone", phone);
		
		final User u = new User();
		
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL,
				new JSONObject(params), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.v("joinusandroid", response.toString());

						try {
							u.setPhone(response.getString("phone"));
							u.setName(response.getString("name"));
							
							getUserListener.onUserRetrieved(u);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Error: ", error.getMessage());
					}
				});

		// add the request object to the queue to be executed
		addToRequestQueue(req);
		
		//joinusServiceLocalImpl.login(u);
		return user;
	}

	@Override
	public void sendLocation(int userId, Location l) {
		// TODO Auto-generated method stub

	}

	public void setCreateMeetingListener(CreateMeetingListener createMeetingListener) {
		this.createMeetingListener = createMeetingListener;
	}
	
	public void setFindMeetingListener(FindMeetingListener findMeetingListener) {
		this.findMeetingListener = findMeetingListener;
	}

	public void setGetMeetingListListener(GetMeetingListListener listener) {
		getMeetingListListener = listener;
	}

	/**
	 * @param getUserListener the getUserListener to set
	 */
	public void setGetUserListener(GetUserListener getUserListener) {
		this.getUserListener = getUserListener;
	}
}
