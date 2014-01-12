package meg.joinus.activities;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class UpcomingEventsLoaderCallbacks 
extends ListActivity 
implements LoaderCallbacks<Cursor> {

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	 @Override 
	 public void onListItemClick(ListView l, View v, int position, long id) {
	        // Do something when a list item is clicked
	 }

}
