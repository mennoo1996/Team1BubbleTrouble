package lan;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import powerups.Powerup;
import powerups.Powerup.PowerupType;
import guigame.GameState;
import guimenu.MainGame;
import guimenu.MenuMultiplayerState;
import logic.BouncingCircle;
import logic.CircleList;
import logic.Coin;
import logic.FloatingScore;
import logic.Logger;

/**
 * Client class which connects to server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Client extends Connector {

    private String host;
    private Socket socket;
    
    private boolean editingCircleList;
	private ArrayList<BouncingCircle> circleList;
    private ArrayList<BouncingCircle> requiredList;
    private int gateNumber;
    
    private static final String LASER = "LASER";
	

	/**
     * Create a new Client connection for LAN multiplayer.
     * @param host Host server address
     * @param portNumber Port number for multiplayer
     * @param mainGame the mainGame that is this Client.
     * @param gameState the gameState that uses this client for messaging.
     */
    public Client(String host, int portNumber, MainGame mainGame, GameState gameState) {
    	super(portNumber, mainGame, gameState);
        this.host = host;
    	this.circleList = new ArrayList<BouncingCircle>();
        this.requiredList = new ArrayList<BouncingCircle>();
        this.editingCircleList = false;
    }

    @Override
    public void run() {
		try {
			socket = new Socket();
			// Connect to socket with timeout
			socket.connect(new InetSocketAddress(host, portNumber), TIMEOUT_ATTEMPT);
			mainGame.setSwitchState(mainGame.getGameState());
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	// Say hello here
        	messageQueue.add("PLAYER NAME "  // send your player's name to host
        		+ mainGame.getPlayerList().getPlayers().get(1).getPlayerName());
			timeLastInput = System.currentTimeMillis();
			// This continues ad infinitum
			while (running) {
				manageHeartbeatCheck();
				while (!this.messageQueue.isEmpty()) {
					writer.println(this.messageQueue.poll());
				}
				readServerCommands();
			}
		} catch (IOException err) {
			processConnectionException(err);
		}
    }

	/**
	 * Process exceptions thrown.
	 * @param err Exception thrown.
	 */
	private void processConnectionException(IOException err) {
		System.out.println(err);
		System.out.println(err.getLocalizedMessage());
		MenuMultiplayerState multiplayerState = (MenuMultiplayerState)
				this.mainGame.getState(mainGame.getMultiplayerState());
		if (err.getMessage().equals("Connection refused")) {
			multiplayerState.addMessage("Connection Refused");
		}
		if (err.getMessage().equals("No connection")) {
			multiplayerState.addMessage("Host disconnected");
		}
		this.mainGame.setSwitchState(mainGame.getMultiplayerState());
	}

	/**
	 * Triggers/ends the heartbeat check for a possible missing connection.
	 * @throws IOException Thrown if connection no longer exists
	 */
	private void manageHeartbeatCheck() throws IOException {
		if (heartBeatCheck
				&& (System.currentTimeMillis() - timeLastInput) >= 2 * TIMEOUT_ATTEMPT) {
			throw new IOException("No connection");
        }
		if (!heartBeatCheck
				&& (System.currentTimeMillis() - timeLastInput) >= TIMEOUT_ATTEMPT) {
            heartBeatCheck = true;
            this.messageQueue.add("HEARTBEAT_CHECK");
        }
	}

	/**
     * Process the commands given by the server.
     */
    public void readServerCommands() {
        try {
			while (reader.ready()) {
				String message = reader.readLine(), message2 = "";
				if (message != null) {
					message2 = message.trim();
				}
				if (message2.startsWith("NEW")) {
					newMessage(message2.replaceFirst("NEW", ""));
				} else if (message2.startsWith("SYSTEM")) {
					systemMessage(message2.replaceFirst("SYSTEM", ""));
				} else if (message2.startsWith("UPDATE")) {
					updateMessage(message2.replaceFirst("UPDATE", ""));
				} else if (message2.startsWith("CIRCLE")) {
					circleMessage(message2.replaceFirst("CIRCLE", ""));
				} else if (message2.startsWith("POWERUP")) {
					powerupMessage(message2.replaceFirst("POWERUP", ""));
				} else if (message2.startsWith("COIN")) {
					coinMessage(message2.replaceFirst("COIN", ""));
				} else if (message2.startsWith("PLAYER")) {
					playerMessage(message2.replaceFirst("PLAYER", ""));
				}
				readServerCommands2(message2);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
    }

	/**
     * Continue processing the commands given by the server.
     * @param message2	the message to process
     */
    private void readServerCommands2(String message2) {
    	if (message2.startsWith("HEARTBEAT_CHECK")) {
			this.sendMessage("HEARTBEAT_ALIVE");
		} else if (message2.startsWith(LASER)) {
			laserMessage(message2.replaceFirst(LASER, ""));
		} else if (message2.startsWith("FLOATINGSCORE")) {
			floatingMessage(message2.replaceFirst("FLOATINGSCORE", ""));
		} else if (message2.startsWith("SPLIT")) {
			splitMessage(message2.replaceFirst("SPLIT", ""));
		} else if (message2.startsWith("SHUTDOWN")) {
			MenuMultiplayerState multiplayerState = (MenuMultiplayerState)
					this.mainGame.getState(mainGame.getMultiplayerState());
			multiplayerState.addMessage("Host Quit.");
			mainGame.setSwitchState(mainGame.getMultiplayerState());
			mainGame.killMultiplayer();
		}
		// heartBeat reset
		heartBeatCheck = false;
		timeLastInput = System.currentTimeMillis();
    }
    
    
    /**
     * Add a FloatingScore to the list.
     * @param message String containing the FloatingScore to add
     */
    private void floatingMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
		gameState.getInterfaceHelper().getFloatingScores().add(new FloatingScore(stringList[2],
				Float.parseFloat(stringList[0]), Float.parseFloat(stringList[1])));
	}

    
    /**
     * Process a message about a circle.
     * @param message the message to process
     */
    private void circleMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	synchronized (this.circleList) {
    		BouncingCircle circle = new BouncingCircle(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), Float.parseFloat(stringList[2]),
    				Float.parseFloat(stringList[THREE]), Float.parseFloat(stringList[FOUR]),
    				Float.parseFloat(stringList[FIVE]), Integer.parseInt(stringList[SEVEN]));
    		circle.setMultiplier(Float.parseFloat(stringList[SIX]));
        	this.circleList.add(circle);
    	}
    }
    
    /**
     * Process a message about an update.
     * @param message the message to process
     */
    private void updateMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith("CIRCLELIST")) {
    		circleListMessage(message2.replaceFirst("CIRCLELIST", ""));
    	} else if (message2.startsWith("REQUIREDLIST")) {
    		requiredListMessage(message2.replaceFirst("REQUIREDLIST", ""));
    	}
    	
    }
    
    /**
     * Process a message about the circleList.
     * @param message	the message to process
     */
    private void requiredListMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	this.gateNumber = Integer.parseInt(stringList[1]);
    	
    	if (stringList[0].equals("START") && !this.editingCircleList) {
    		this.requiredList = new ArrayList<BouncingCircle>();
    	} else if (stringList[0].equals("END") && this.editingCircleList) {
    		gameState.getGateHelper().getGateList().get(gateNumber).setRequired(requiredList);
    	}
    }
    
    /**
     * Process a message about the circleList.
     * @param message	the message to process
     */
    private void circleListMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.equals("START") && !this.editingCircleList) {
    		this.circleList = new ArrayList<BouncingCircle>();
    		this.editingCircleList = true;
    	} else if (message2.equals("END") && this.editingCircleList) {
    		this.editingCircleList = false;
    		synchronized (gameState.getCirclesHelper().getCircleList()) {
        		gameState.getCirclesHelper().setCircleList(new CircleList(circleList));
    		}
    	}
    }
    
	/**
     * Process a new message.
     * @param message the message to process
     */
    private void newMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith("PLAYERLOCATION")) {
    		playerLocation(message2.replaceFirst("PLAYERLOCATION", ""));
    	} else if (message2.startsWith(LASER)) {
    		newLaserMessage(message2.replaceFirst(LASER, ""));
    	}
    }
    
    /**
     * Send a powerup to the host.
     * @param powerup the powerup to send
     */
    public void updatePowerupsAdd(Powerup powerup) {
    	sendMessage(powerup.toString() + "ADD ");
    }
    
    /**
     * Process a message about the location of a player.
     * @param message the message to process
     */
    private void playerLocation(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	int id = Integer.parseInt(stringList[0]);
    	float x = Float.parseFloat(stringList[1]);
    	float y = Float.parseFloat(stringList[2]);
    	
    	mainGame.getPlayerList().getPlayers().get(id).getLogicHelper().setX(x);
    	mainGame.getPlayerList().getPlayers().get(id).getLogicHelper().setY(y);
    }
    
    /**
     * Process a message about the system.
     * @param message the message to process
     */
    private void systemMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith("LEVEL")) {
    		levelMessage(message2.replaceFirst("LEVEL", ""));
    	} else if (message2.startsWith("COUNTIN")) {
    		countinMessage(message2.replaceFirst("COUNTIN", ""));
    	} else if (message2.startsWith("PAUSE")) {
    		pauseMessage(message2.replaceFirst("PAUSE", ""));
    	} else if (message2.startsWith("LIVES")) {
    		livesMessage(message2.replaceFirst("LIVES", ""));
    	}
    }
    
    /**
     * Process message about lives.
     * @param message	the message to process
     */
    private void livesMessage(String message) {
    	String message2 = message.trim();
    	
    	int lives = Integer.parseInt(message2);
    	mainGame.setLifeCount(lives);
    	mainGame.getPlayerList().setDied(false);
    }
   
    /**
     * Process a message about the coutnin.
     * @param message	the message to process
     */
    private void countinMessage(String message) {
    	String message2 = message.trim();
    	if (message2.equals("STARTED")) {
    		gameState.getLogicHelper().setCountinStarted(true);
    	}
    }
   
    
    /**
     * Process a message about the level.
     * @param message the message to process
     */
    private void levelMessage(String message) {
    	String message2 = message.trim();
    	if (message2.equals("STARTED")) {
    		gameState.getLogicHelper().setLevelStarted(true);
    		gameState.getLogicHelper().setCountinStarted(false);
    	} else if (message2.equals("RESTART")) { 
    		// force override life, level, score etc. Just. In. Case. someone forgets.
    		mainGame.resetLifeCount();
    		mainGame.resetLevelCount();
    		mainGame.setScore(0);
    		mainGame.setSwitchState(mainGame.getGameState());
    	}
    }

    /**
     * Attempt to gracefully shut down the LAN threads.
     * @throws InterruptedException Thrown if shutdown takes longer than expected.
     */
    public void shutdown() throws InterruptedException {
        logger.log("Shutting down client connection", Logger.PriorityLevels.LOW, "lan");
		if (writer != null) {
			writer.println("SHUTDOWN");
			running = false;
		}
        try {
            socket.close();
        } catch (IOException er) {
            logger.log(er.getMessage(), Logger.PriorityLevels.LOW, "lan");
        }
    }

    
    
    /**
     * Process an incoming message about a powerup.
     * @param message the message to process
     */
    private void powerupMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	if (stringList[THREE].equals("ADD")) {
    		addPowerup(stringList);
    	} else if (stringList[THREE].equals("DICTATE")) {
    		dictatePowerup(stringList);
    	} else if (stringList[THREE].equals("GRANT")) {
    		grantPowerup(stringList);
    	}
    }

    /**
     * Add a powerup to the level on the client side.
     * @param stringList information on powerup
     */
    private void addPowerup(String[] stringList) {
    	synchronized (gameState.getItemsHelper().getDroppedPowerups()) {
    		PowerupType type = PowerupType.SHIELD;
    		if (stringList[2].equals("SHIELD")) {
    			type = PowerupType.SHIELD; // shield added
    		} else if (stringList[2].equals("SPIKY")) {
    			type = PowerupType.SPIKY; // spiky added
    		} else if (stringList[2].equals("INSTANT")) {
    			type = PowerupType.INSTANT; // inst added
    		} else if (stringList[2].equals("HEALTH")) {
    			type = PowerupType.HEALTH; // health added
    		} else if (stringList[2].equals("FREEZE")) {
    			type = PowerupType.FREEZE; // freeze added 
    		} else if (stringList[2].equals("SLOW")) {
    			type = PowerupType.SLOW; // slow added 
    		} else if (stringList[2].equals("FAST")) {
    			type = PowerupType.FAST; // fast added 
    		} else if (stringList[2].equals("RANDOM")) {
    			type = PowerupType.RANDOM; // random added
    		}
    		if (type != null) {
        		gameState.getItemsHelper().getDroppedPowerups().add(
        				new Powerup(Float.parseFloat(stringList[0]),
    					Float.parseFloat(stringList[1]), type));
    		}
    	}
    }

	/**
     * Remove a powerup from the level, and give it to the host player.
     * @param stringList information on powerup
     */
    private void dictatePowerup(String[] stringList) {
    	PowerupType type = PowerupType.SHIELD;
    	if (stringList[2].equals("SHIELD")) {
    		type = PowerupType.SHIELD;
		} else if (stringList[2].equals("SPIKY")) {
			type = PowerupType.SPIKY;
		} else if (stringList[2].equals("INSTANT")) {
			type = PowerupType.INSTANT;
		} else if (stringList[2].equals("HEALTH")) {
			type = PowerupType.HEALTH;
		} else if (stringList[2].equals("FREEZE")) {
			type = PowerupType.FREEZE;
		} else if (stringList[2].equals("SLOW")) {
			type = PowerupType.SLOW;
		} else if (stringList[2].equals("FAST")) {
			type = PowerupType.FAST; } else if (stringList[2].equals("RANDOM")) {
			type = PowerupType.RANDOM; }
    	ArrayList<Powerup> poweruplist = new ArrayList<Powerup>();
		for (Powerup powerup : gameState.getItemsHelper().getDroppedPowerups()) {
			if (powerup.getxId() == Float.parseFloat(stringList[0])
					&& powerup.getyId() == Float.parseFloat(stringList[1])) {
				poweruplist.add(powerup);
				synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
					gameState.getInterfaceHelper().getFloatingScores().
					add(new FloatingScore(powerup));
				}
				mainGame.getPlayerList().getPlayers().get(0).addPowerup(type);
			}
		} gameState.getItemsHelper().getDroppedPowerups().removeAll(poweruplist);
	}
    
    /**
     * Grant a powerup to the client's player.
     * @param stringList the IDs of the powerups
     */
    private void grantPowerup(String[] stringList) {
    	if (stringList[2].equals("SHIELD")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SHIELD);
    	} else if (stringList[2].equals("SPIKY")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SPIKY);
    	} else if (stringList[2].equals("INSTANT")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.INSTANT);
    	} else if (stringList[2].equals("HEALTH")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.HEALTH);
    	} else if (stringList[2].equals("FREEZE")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.FREEZE);
    	} else if (stringList[2].equals("SLOW")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SLOW);
    	} else if (stringList[2].equals("FAST")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.FAST);
    	} else if (stringList[2].equals("RANDOM")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.RANDOM); }
    	synchronized (gameState.getItemsHelper().getDroppedPowerups()) {
    		ArrayList<Powerup> poweruplist = new ArrayList<Powerup>();
    		for (Powerup powerup : gameState.getItemsHelper().getDroppedPowerups()) {
    			if (powerup.getxId() == Float.parseFloat(stringList[0])
    					&& powerup.getyId() == Float.parseFloat(stringList[1])) {
    				poweruplist.add(powerup);
    				synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
    					gameState.getInterfaceHelper().getFloatingScores().
    					add(new FloatingScore(powerup));
    				} }
    		}
    		gameState.getItemsHelper().getDroppedPowerups().removeAll(poweruplist); }
	}
    
    /**
     * Ask to use a powerup on the client's player.
     * @param powerup the powerup to confirm
     */
    public void pleaPowerup(Powerup powerup) {
    	sendMessage(powerup.toString() + "PLEA ");
    }
    
	/**
     * Process an incoming message about a coin.
     * @param message the message to process
     */
    private void coinMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	if (stringList[THREE].equals("ADD")) {
    		addCoin(stringList);
    	} else if (stringList[THREE].equals("DICTATE")) {
    		dictateCoin(stringList);
    	} else if (stringList[THREE].equals("GRANT")) {
    		grantCoin(stringList);
    	}
    }
    
    /**
     * Add a coin to the level, client-sided.
     * @param stringList description of the coin
     */
    private void addCoin(String[] stringList) {
    	synchronized (gameState.getItemsHelper().getDroppedCoins()) {
        	gameState.getItemsHelper().getDroppedCoins().add(
        			new Coin(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), Boolean.parseBoolean(stringList[2])));
    	}
    }
    
    /**
     * Dictate that a coin goes to the host player.
     * @param stringList description of the coin
     */
    private void dictateCoin(String[] stringList) {
    	ArrayList<Coin> coinlist = new ArrayList<Coin>();
    	synchronized (gameState.getItemsHelper().getDroppedCoins()) {
    		for (Coin coin : gameState.getItemsHelper().getDroppedCoins()) {
    			if (coin.getxId() == Float.parseFloat(stringList[0])
    					&& coin.getyId() == Float.parseFloat(stringList[1])) {
    				coinlist.add(coin);
    				synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
    					gameState.getInterfaceHelper().getFloatingScores().
    					add(new FloatingScore(coin));
    				}
    			}
    		}
    		gameState.getItemsHelper().getDroppedCoins().removeAll(coinlist);
    	}
    }
    
    /**
     * Grant a coin to a player.
     * @param stringList the IDs of the coins
     */
    private void grantCoin(String[] stringList) {
    	ArrayList<Coin> coinlist = new ArrayList<Coin>();
    	synchronized (gameState.getItemsHelper().getDroppedCoins()) {
    		for (Coin coin : gameState.getItemsHelper().getDroppedCoins()) {
    			if (coin.getxId() == Float.parseFloat(stringList[0])
    					&& coin.getyId() == Float.parseFloat(stringList[1])) {
    				coinlist.add(coin);
    				synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
    					gameState.getInterfaceHelper().getFloatingScores().
    					add(new FloatingScore(coin));
    				}
    			}
    		}
    		gameState.getItemsHelper().getDroppedCoins().removeAll(coinlist);
    	}
    }
    
    /**
     * Confirm a coin to the host.
     * @param coin the coin to confirm
     */
    public void pleaCoin(Coin coin) {
    	sendMessage(coin.toString() + "PLEA ");
    }

    /**
     * @return Whether or not the client has connected to the remote server
     */
    public boolean connectedToServer() {
        return this.socket.isConnected();
    }
    
    
}
