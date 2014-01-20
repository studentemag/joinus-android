package mag.joinus.app;

import mag.joinus.service.JoinusService;
import mag.joinus.service.JoinusServiceImpl;
import android.app.Application;

public class JoinusApplication extends Application {
    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static JoinusApplication sInstance;
    
    private JoinusService service;

	@Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        sInstance = this;
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
	public JoinusService getService() {
		if (service == null) 
			service = new JoinusServiceImpl();//TODO passare il context
		
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(JoinusService service) {
		this.service = service;
	}
}
