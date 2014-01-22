package mag.joinus.app;

import mag.joinus.model.Meeting;
import mag.joinus.model.User;
import mag.joinus.service.JoinusServiceImpl;
import android.app.Application;

public class JoinusApplication extends Application {
    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static JoinusApplication sInstance;
    
    private JoinusServiceImpl service;
    private User user;
    private Meeting meeting;
    
	@Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        sInstance = this;
        service = new JoinusServiceImpl(this);
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized JoinusApplication getInstance() {
        return sInstance;
    }

    /**
	 * @return the service
	 */
	public JoinusServiceImpl getService() {		
		return service;
	}
}
