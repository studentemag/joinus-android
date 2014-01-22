package mag.joinus.app;

import mag.joinus.service.JoinusServiceImpl;
import android.app.Application;

public class JoinusApplication extends Application {
    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static JoinusApplication sInstance;
    
    private JoinusServiceImpl service;

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
