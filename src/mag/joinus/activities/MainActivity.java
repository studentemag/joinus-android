package mag.joinus.activities;

import mag.joinus.R;
import mag.joinus.activities.upcomingmeetings.UpcomingEventsActivity;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.User;
import mag.joinus.service.JoinusServiceImpl;
import mag.joinus.service.listeners.GetUserListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements GetUserListener {
	
	private JoinusServiceImpl joinusServiceImpl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		joinusServiceImpl = JoinusApplication.getInstance().getService();
		joinusServiceImpl.setGetUserListener(this);
		
		Log.v("joinUsAndroid", "starting MainActivity");
		
		setPhoneNumber();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void signIn(View view) {
		Log.v("joinUsAndroid", "signIn called");
		User u = new User();
		EditText et = (EditText) findViewById(R.id.phoneNumber);
		String phone = new String(et.getText().toString());
		if (phone.isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.phoneisempty, Toast.LENGTH_LONG).show();
			return;
		}
		u.setPhone(phone);
		
		et = (EditText) findViewById(R.id.username);
		u.setName(et.getText().toString());
		
		findViewById(R.id.main_progressbar).setVisibility(View.VISIBLE);
		joinusServiceImpl.login(u);
	}
	
	public void setPhoneNumber() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String ownPhoneNumber = tm.getLine1Number();
		
		EditText et = (EditText) findViewById(R.id.phoneNumber);
		et.setText(ownPhoneNumber);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onUserRetrieved(User user) {
		JoinusApplication.getInstance().setUser(user);
		findViewById(R.id.main_progressbar).setVisibility(View.GONE);

		Intent intent = new Intent(this, UpcomingEventsActivity.class);
		startActivity(intent);
	}

	@Override
	public void onUserRetrievedError() {
		findViewById(R.id.main_progressbar).setVisibility(View.GONE);
		Toast.makeText(getApplicationContext(), R.string.main_checkinternet, Toast.LENGTH_LONG).show();
	}

}
