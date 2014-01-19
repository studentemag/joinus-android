package mag.joinus.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mag.joinus.app.JoinusApplication;
import mag.joinus.model.Location;
import mag.joinus.model.Meeting;
import mag.joinus.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class JoinusServiceImpl implements JoinusService{

	private static JoinusService service = null;
	
	public static JoinusService getService(){
		if (service==null)
			service = new JoinusServiceImpl();
		return service;
	}
	

	@Override
	public Location getLocationFromAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting createMeeting(String title, Date date, Location location,
			User mc, List<String> phones) {
		// TODO Auto-generated method stub
		
		final String URL = "http://93.65.216.110:8080/events";
		// Post params to be sent to the server
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("title", "festa di mario");
		params.put("latitude","10");

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
		       new Response.Listener<JSONObject>() {
		           @Override
		           public void onResponse(JSONObject response) {
		               Log.v("joinusandroid", response.toString());
		               Meeting me = new Meeting();
		               try {
						me.setTitle(response.getString("title"));
						me.setDate(response.getLong("date"));
						me.setPlace(response.getString("place"));
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
		JoinusApplication.getInstance().addToRequestQueue(req);
		
		return null;
	}
	
}
