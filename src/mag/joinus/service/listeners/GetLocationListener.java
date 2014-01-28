package mag.joinus.service.listeners;

import java.util.List;

import mag.joinus.model.UserLocation;

public interface GetLocationListener {

	public void onLocationsRetrieved(List<UserLocation> uLocs);
}
