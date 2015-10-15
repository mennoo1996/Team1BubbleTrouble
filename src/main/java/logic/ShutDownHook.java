package logic;

import guimenu.MainGame;


/**
 * Class that supports writing log to file during shutdown.
 */
public class ShutDownHook {
	
	private Logger logger = Logger.getInstance();
	private MainGame game;
	
	/**
	 * Create a new instance of ShutDownHook.
	 * @param game the maingame we still need for killing multiplayer.
	 */
	public ShutDownHook(MainGame game) {
		this.game = game;
	}

	/**
	 * Add a log file write hook on shutdown, and (if multiplayer) attempt to send one last message.
	 */
	public void attachShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.log("Shutdown requested", 
						Logger.PriorityLevels.HIGH, "shutdown");
				logger.writeToFile();
				if (game.isLanMultiplayer()) {
					game.killMultiplayer();
				}
			}
		});
		logger.log("Shutdown hook attacked", 
				Logger.PriorityLevels.MEDIUM, "shutdown");
	}
}
