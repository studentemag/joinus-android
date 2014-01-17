package mag.joinus.activities;


import java.util.List;

import mag.joinus.model.Meeting;
import meg.joinus.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventArrayAdapter extends ArrayAdapter<Meeting>{
	Context context;
	List<Meeting> events;
	
	public EventArrayAdapter(Context context, int resource, List<Meeting> events) {
	    super(context, resource, events);
	    this.events = events;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
	        LayoutInflater vi;
	        vi = LayoutInflater.from(getContext());
	        v = vi.inflate(R.layout.listitem_upcoming_events, null);
	    }
		
		Meeting e = events.get(position);
		
		if (e != null) {
			TextView tt = (TextView) v.findViewById(R.id.firstLine);
	        TextView tt1 = (TextView) v.findViewById(R.id.secondLine);
	        
	        tt.setText(e.getTitle());
	        tt1.setText(e.getDate().toString());
		}

		return v;
	}

}
