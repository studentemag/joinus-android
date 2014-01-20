package mag.joinus.service;

import java.util.HashMap;
import java.util.List;

import mag.joinus.activities.CreateMeetingListener;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Location;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class JoinusServiceImpl implements JoinusService {

	
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
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(JoinusApplication.getInstance().getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
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
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     * 
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

	@Override
	public Location getLocationFromAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting createMeeting(CreateMeetingListener listener, String title, long date, Location location,
			User mc, List<String> phones) {
		
		final String URL = "http://93.65.216.110:8080/events";
		final CreateMeetingListener finalListener = listener;
		// Post params to be sent to the server
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("title", "festa di mario");
		params.put("latitude","10");

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
		       new Response.Listener<JSONObject>() {
		           @Override
		           public void onResponse(JSONObject response) {
		               Log.v("joinusandroid", response.toString());
		               
		               Meeting m = new Meeting();
		               try {
						m.setTitle(response.getString("title"));
						m.setDate(response.getLong("date"));
						m.setAddress(response.getString("place"));
						
						finalListener.onMeetingCreated(m);
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
		
		return null;
	}
	
}
