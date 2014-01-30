package mag.joinus.activities.upcomingmeetings;


import java.text.DateFormat;
import java.util.List;

import mag.joinus.R;
import mag.joinus.model.Meeting;

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
			TextView itemLine1 = (TextView) v.findViewById(R.id.firstLine);
	        TextView itemLine2 = (TextView) v.findViewById(R.id.secondLine);
	        
	        itemLine1.setText(e.getTitle());
	        
	        String date = DateFormat.getDateInstance(DateFormat.LONG).format(e.getDate());
	        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(e.getDate());
	        itemLine2.setText(date + " " + time + " at " + e.getAddress());
		}

		return v;
	}

}
