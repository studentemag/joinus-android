package mag.joinus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.model.UserLocation;
import mag.joinus.service.listeners.CreateMeetingListener;
import mag.joinus.service.listeners.FindMeetingListener;
import mag.joinus.service.listeners.GetLocationsListener;
import mag.joinus.service.listeners.GetMeetingListListener;
import mag.joinus.service.listeners.GetUserListener;
import mag.joinus.service.listeners.ShareLocationListener;

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
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class JoinusServiceImpl implements JoinusService {
	private final String BASE_URL = "http://ec2-54-194-206-128.eu-west-1.compute.amazonaws.com:8080";
	
	private JoinusServiceLocal joinusServiceLocal;
	
	private CreateMeetingListener createMeetingListener;
	private FindMeetingListener findMeetingListener;
	private GetMeetingListListener getMeetingListListener;
	private GetUserListener getUserListener;
	private ShareLocationListener shareLocationListener = null;
	private GetLocationsListener getLocationsListener = null;

	public JoinusServiceImpl(Context context){
		joinusServiceLocal = OpenHelperManager.getHelper(context, JoinusServiceLocal.class);
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
	public Meeting acceptInvitationTo(int meetingId, User user) {
		final String URL = BASE_URL + "/events/" + meetingId + "/accept";
		
		JSONObject body = user.toJson();
		Log.v("JoinusServiceImpl.acceptInvitationTo", 
				"requestBody: " + body.toString());
		
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL,
				body, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.v("JoinusServiceImpl.acceptInvitationTo", 
						"responseBody: " + response.toString());

				Meeting m = new Meeting(response);

				findMeetingListener.onMeetingFound(m);
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
	public Meeting denyInvitationTo(int meetingId, User user) {
		final String URL = BASE_URL + "/events/" + meetingId + "/deny";
		
		JSONObject body = user.toJson();
		Log.v("JoinusServiceImpl.acceptInvitationTo", 
				"requestBody: " + body.toString());
		
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL,
				body, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.v("JoinusServiceImpl.acceptInvitationTo", 
						"responseBody: " + response.toString());

				Meeting m = new Meeting(response);

				findMeetingListener.onMeetingFound(m);
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
	public Meeting addParticipantsToMeeting(List<User> users, int meetingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting createMeeting(Meeting m) {
		final String URL = BASE_URL + "/events";

		JSONObject body = m.toJson();
		Log.v("JoinusServiceImpl.createMeeting", 
				"requestBody: " + body.toString());
		
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL,
				body, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.v("JoinusServiceImpl.createMeeting", 
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
	public Location getLocationFromAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getUpcomingEvents(String phone) {
		final String URL = BASE_URL + "/users/" + phone + "/events";

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
							joinusServiceLocal.getUpcomingEventsWrite(mList);
							getMeetingListListener.onMeetingListRetrieved(mList);
						} catch (JSONException e) {
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
		
		return joinusServiceLocal.getUpcomingEvents(phone);
	}

	@Override
	public User login(User user) {
		String name = user.getName();
		String phone = user.getPhone();
		
		final String URL = BASE_URL + "/users";

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
	public List<UserLocation> getLocations(int meeting_id) {
		final String URL = BASE_URL + "/events/" + meeting_id + "/locations";

		// Default method is GET
		JsonArrayRequest req = new JsonArrayRequest(URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.v("joinusandroid", "getLocations: " + response.toString());
						List<UserLocation> uLocs = new ArrayList<UserLocation>();
						try {
							if (response != null) {
								for (int i = 0; i < response.length(); i++) {
									UserLocation u = new UserLocation(response.getJSONObject(i));
									uLocs.add(u);
								}
							}
							
							if (getLocationsListener != null)
								getLocationsListener.onLocationsRetrieved(uLocs);
						} catch (JSONException e) {
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
		
		return null;
	}

	@Override
	public void shareLocation(String phone, UserLocation uLoc) {
		final String URL = BASE_URL + "/users/" + phone + "/locations";

		JSONObject body = uLoc.toJson();
		Log.v("JoinusServiceImpl.shareLocation", 
				"requestBody: " + body.toString());
		
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL,
				body, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.v("JoinusServiceImpl.shareLocation", 
								"response received");
						
						if (shareLocationListener != null)
							shareLocationListener.onLocationShared();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Error: ", error.getMessage());
					}
				});

		// add the request object to the queue to be executed
		addToRequestQueue(req);
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

	/**
	 * @return the shareLocationListener
	 */
	public ShareLocationListener getShareLocationListener() {
		return shareLocationListener;
	}

	/**
	 * @param shareLocationListener the shareLocationListener to set
	 */
	public void setShareLocationListener(ShareLocationListener shareLocationListener) {
		this.shareLocationListener = shareLocationListener;
	}

	/**
	 * @return the getLocationListener
	 */
	public GetLocationsListener getGetLocationsListener() {
		return getLocationsListener;
	}

	/**
	 * @param getLocationListener the getLocationListener to set
	 */
	public void setGetLocationsListener(GetLocationsListener getLocationsListener) {
		this.getLocationsListener = getLocationsListener;
	}
}
