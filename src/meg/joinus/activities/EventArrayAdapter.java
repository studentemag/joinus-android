package meg.joinus.activities;


import java.util.List;

import meg.joinus.R;
import meg.joinus.model.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventArrayAdapter extends ArrayAdapter<Event>{
	Context context;
	List<Event> events;
	
	public EventArrayAdapter(Context context, int resource, List<Event> events) {
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
		
		Event e = events.get(position);
		
		if (e != null) {
			TextView tt = (TextView) v.findViewById(R.id.firstLine);
	        TextView tt1 = (TextView) v.findViewById(R.id.secondLine);
	        
	        tt.setText(e.getTitle());
	        tt1.setText(e.getDate().toString());
		}

		return v;
	}

}
