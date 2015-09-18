package logic;

import gui.MainGame;

public class ShutDownHook {
	
	private MainGame mainGame;
	
	public ShutDownHook(MainGame mainGame) {
		this.mainGame = mainGame;
	}
	
	public void attachShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Inside add shutdown hook");
				mainGame.getLogger().writeToFile();
			}
		});
		System.out.println("Shutdown hook attached");
	}
}
