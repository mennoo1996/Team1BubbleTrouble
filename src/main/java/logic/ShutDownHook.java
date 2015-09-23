package logic;

import gui.MainGame;

/**
 * Class that supports writing log to file during shutdown.
 */
public class ShutDownHook {
	
	private MainGame mainGame;

	/**
	 * Create a new instance of ShutDownHook.
	 * @param mainGame The game being played
	 */
	public ShutDownHook(MainGame mainGame) {
		this.mainGame = mainGame;
	}

	/**
	 * Add a log file write hook on shutdown.
	 */
	public void attachShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				mainGame.getLogger().log("Shutdown requested", 
						Logger.PriorityLevels.HIGH, "shutdown");
				mainGame.getLogger().writeToFile();
			}
		});
		mainGame.getLogger().log("Shutdown hook attacked", 
				Logger.PriorityLevels.MEDIUM, "shutdown");
	}
}
