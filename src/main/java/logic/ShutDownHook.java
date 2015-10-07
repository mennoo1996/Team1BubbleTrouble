package logic;


/**
 * Class that supports writing log to file during shutdown.
 */
public class ShutDownHook {
	
	private Logger logger = Logger.getInstance();

	/**
	 * Create a new instance of ShutDownHook.
	 */
	public ShutDownHook() {
		
	}

	/**
	 * Add a log file write hook on shutdown.
	 */
	public void attachShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.log("Shutdown requested", 
						Logger.PriorityLevels.HIGH, "shutdown");
				logger.writeToFile();
			}
		});
		logger.log("Shutdown hook attacked", 
				Logger.PriorityLevels.MEDIUM, "shutdown");
	}
}
