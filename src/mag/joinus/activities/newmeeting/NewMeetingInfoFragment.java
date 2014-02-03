package mag.joinus.activities.newmeeting;

import java.util.ArrayList;
import java.util.List;

import mag.joinus.R;
import mag.joinus.app.JoinusApplication;
import mag.joinus.model.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewMeetingInfoFragment extends Fragment{

	private final int PICK_CONTACT = 0;

	List<User> guests;
	
	private Context context;
	private View rootView;
	
	
	public NewMeetingInfoFragment(){
		super();
		this.context=JoinusApplication.getInstance();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_newmeeting_info,
				container, false);
		
		Button button = (Button) rootView.findViewById(R.id.newmeeting_pickcontact);
		button.setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		             Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		             startActivityForResult(intent, PICK_CONTACT);
		         }
		     });
		
		EditText titleEditText = (EditText) rootView.findViewById(R.id.newmeeting_info_title);
		titleEditText.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	            JoinusApplication.getInstance().getMeetingToCreate().setTitle(s.toString());
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 
		
		this.guests=new ArrayList<User>();
		JoinusApplication.getInstance().getMeetingToCreate().setGuests(guests);
		
		return rootView;
	}
		
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		Log.v("NewMeetingInfoFragment","onActivityResult");
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Cursor c = context.getContentResolver().query(contactData,
						null, null, null, null);
				if (c.moveToFirst()) {
					String id = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

					String name = c.getString(c
							.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
					String hasPhone = c
							.getString(c
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

					if (hasPhone.equalsIgnoreCase("1")) {
						Cursor phones = context
								.getContentResolver()
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = ?", new String[] { id },
										null);
						phones.moveToFirst();
						String cNumber = phones
								.getString(phones
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						
						cNumber=cNumber.replaceAll(" ", "");
						cNumber=cNumber.substring(cNumber.length()-10,cNumber.length());
						
						User u = new User();
						u.setPhone(cNumber);
						u.setName(name);
						this.guests.add(u);
						
						TextView guestsTextView = (TextView) rootView.findViewById(R.id.newmeeting_info_participants);
						guestsTextView.setText(guestsTextView.getText()+name+", ");
					}
				}
			}
		}
	}
	
	
	

}
