package mag.joinus.activities.newmeeting;

import mag.joinus.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NewMeetingActivity extends Activity {

	private final int PICK_CONTACT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_meeting);
		
		
		Button button = (Button) findViewById(R.id.pickcontact);

		button.setOnClickListener(new OnClickListener() 
		    {
		        @Override
		        public void onClick(View v) 
		        {
		             Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		             startActivityForResult(intent, PICK_CONTACT);
		         }
		     });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_meeting, menu);
		return true;
	}

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch(reqCode) {
			case (PICK_CONTACT):
				if (resultCode == Activity.RESULT_OK) {
					Uri contactData = data.getData();
					Cursor c = getContentResolver().query(contactData, null, null, null, null);
					//Cursor c = managedQuery(contactData, null, null, null, null);
					if (c.moveToFirst()) {
						String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
		
						String hasPhone =
								c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
	
						if (hasPhone.equalsIgnoreCase("1")) {
							Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
									//ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[] { id }, null);
							phones.moveToFirst();
							String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_LONG).show();
							Log.v("joinus android", cNumber);
						}
					}
				}
		}
	}
}
