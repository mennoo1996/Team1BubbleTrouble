package lan;

import gui.GameState;
import gui.MainGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import logic.BouncingCircle;
import logic.Coin;
import logic.FloatingScore;
import logic.Logger;
import logic.Powerup;
import logic.Player.Movement;
import logic.Powerup.PowerupType;
import logic.Weapon;

/**
 * Client class which connects to server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Client implements Runnable {

    private int portNumber;
    private String host;
    private Socket socket;
    private boolean isConnected;
    private Logger logger;
    private Queue<String> messageQueue;
    private PrintWriter writer;
    private BufferedReader reader;
    private MainGame mainGame;
    private GameState gameState;
	private long timeLastInput;
	private boolean heartBeatCheck;
    
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
	private static final int TIMEOUT_ATTEMPT = 10000;
	private static final int MENU_MULTIPLAYER_STATE = 4;
    /**
     * Create a new Client connection for LAN multiplayer.
     * @param host Host server address
     * @param portNumber Port number for multiplayer
     * @param mainGame the mainGame that is this Client.
     * @param gameState the gameState that uses this client for messaging.
     */
    public Client(String host, int portNumber, MainGame mainGame, GameState gameState) {
        this.host = host;
        this.mainGame = mainGame;
        this.gameState = gameState;
        this.portNumber = portNumber;
        this.isConnected = false;
        this.messageQueue = new LinkedList<>();
    }

    @Override
    public void run() {
		try {
			System.out.println("CLIENT.call");
			socket = new Socket();
			// Connect to socket with timeout
			socket.connect(new InetSocketAddress(host, portNumber), TIMEOUT_ATTEMPT);
			mainGame.setSwitchState(mainGame.getGameState());
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	// Say hello here
        	messageQueue.add("PLAYER NAME "  // send your player's name to host
        		+ mainGame.getPlayerList().getPlayers().get(1).getPlayerName());
			System.out.println("Connected to server");
			timeLastInput = System.currentTimeMillis();
			// This continues ad infinitum
			while (true) {
				manageHeartbeatCheck();
				while (!this.messageQueue.isEmpty()) {
					System.out.println("sending message: " + this.messageQueue.peek());
					writer.println(this.messageQueue.poll());
				}
				readServerCommands();
			}
		} catch (IOException err) {
			System.out.println(err);
			System.out.println(err.getLocalizedMessage());
			this.mainGame.setSwitchState(mainGame.getMultiplayerState());
		}
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
     * Process server commands.
     */
    private void readServerCommands() {
        try {
			while (reader.ready()) {
				String message = reader.readLine();
				//System.out.println("received message: " + message);
				String message2 = message.trim();
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
				timeLastInput = System.currentTimeMillis();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
    }
    
    /**
     * second part of the method that reads server commands.
     * @param message2	the message
     */
    private void readServerCommands2(String message2) {
    	if (message2.startsWith("HEARTBEAT_ALIVE")) {
			heartBeatCheck = false;
		} else if (message2.startsWith("LASER")) {
			laserMessage(message2.replaceFirst("LASER", ""));
		}
    }
    
    /**
     * Message about lasers.
     * @param message	the message
     */
    private void laserMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.startsWith("DONE")) {
    		laserDoneMessage(message2.replaceFirst("DONE", ""));
    	}
    }
    
    /**
     * Message about a laser that is done.
     * @param message	the message
     */
    private void laserDoneMessage(String message) {
    	String message2 = message.trim();
    	
    	int id = Integer.parseInt(message2);
    	gameState.getWeaponList().getWeaponList().get(id).setVisible(false);
    }
    
    /**
     * Process a message about the player.
     * @param message the message to process
     */
    private void playerMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.startsWith("MOVEMENT")) {
    		movementMessage(message2.replaceFirst("MOVEMENT", ""));
    	} else if (message2.startsWith("DEAD")) {
    		deadMessage(message2.replaceFirst("DEAD", ""));
    	} else if (message2.startsWith("NAME")) {
    		nameMessage(message2.replaceFirst("NAME", ""));
    	}
    	
    	
    }
    
    /**
     * Work through the name message from the host.
     * @param message containing their player name.
     */
    private void nameMessage(String message) {
    	String message2 = message.trim();
    	mainGame.getPlayerList().getPlayers().get(0).setPlayerName(message2);
    }
    
    /**
     * Process a message about a player death.
     * @param message the message to process
     */
    private void deadMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.equals("HOST")) {
    		mainGame.getPlayerList().playerDeath(mainGame);
    	}
    }
    
    /**
     * Process a message about the movement of a player.
     * @param message the message to process
     */
    private void movementMessage(String message) {
    	String message2 = message.trim();
    
    	if (message2.startsWith("STARTED")) {
    		movementStarted(message2.replaceFirst("STARTED", ""));
    	} else if (message2.startsWith("STOPPED")) {
    		movementStopped(message2.replaceFirst("STOPPED", ""));
    	}
    }
    
    /**
     * Process a message about the start of a movement.
     * @param message the message to process
     */
    private void movementStarted(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");

    	int playerNumber = Integer.parseInt(stringList[0]);
    	float x = Float.parseFloat(stringList[1]);
    	float y = Float.parseFloat(stringList[2]);
        String direction = stringList[THREE];
    
        mainGame.getPlayerList().getPlayers().get(playerNumber).setX(x);
        mainGame.getPlayerList().getPlayers().get(playerNumber).setY(y);
        
        if (direction.equals("LEFT")) {
        	mainGame.getPlayerList().getPlayers().get(playerNumber).setMovingLeft(true);
        	mainGame.getPlayerList().getPlayers().get(playerNumber).setMovement(Movement.LEFT);
        } else if (direction.equals("RIGHT")) {
        	mainGame.getPlayerList().getPlayers().get(playerNumber).setMovingRight(true);
        	mainGame.getPlayerList().getPlayers().get(playerNumber).setMovement(Movement.RIGHT);
        }
    }
    
    /**
     * Process a message about a movement that stopped.
     * @param message the message
     */
    private void movementStopped(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");

    	int playerNumber = Integer.parseInt(stringList[0]);
    	float x = Float.parseFloat(stringList[1]);
    	float y = Float.parseFloat(stringList[2]);
    	
    	mainGame.getPlayerList().getPlayers().get(playerNumber).setX(x);
        mainGame.getPlayerList().getPlayers().get(playerNumber).setY(y);
        
    	mainGame.getPlayerList().getPlayers().get(playerNumber).setMovingRight(false);
    	mainGame.getPlayerList().getPlayers().get(playerNumber).setMovingLeft(false);
    	mainGame.getPlayerList().getPlayers().get(playerNumber).setMovement(Movement.NO_MOVEMENT);
    }
    
    /**
     * Process a message about a player that started moving.
     * @param x the x position of the player
     * @param y the y position of the player
     * @param playerNumber the player number
     * @param direction the direction in which the player started moving
     */
    public void playerStartedMoving(float x, float y, int playerNumber, String direction) {
    	String message = "PLAYER MOVEMENT STARTED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y  + " " + direction;
    	sendMessageToHost(message);
    }
    
    /**
     * Process a message about a player that stopped moving.
     * @param x the new x position of the player
     * @param y the new y position of the player
     * @param playerNumber the player number
     */
    public void playerStoppedMoving(float x, float y, int playerNumber) {
    	String message = "PLAYER MOVEMENT STOPPED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y;
    	sendMessageToHost(message);
    }
    
    /**
     * Process a message about a circle.
     * @param message the message to process
     */
    private void circleMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	gameState.getCircleList().add(new BouncingCircle(Float.parseFloat(stringList[0]),
				Float.parseFloat(stringList[1]), Float.parseFloat(stringList[2]),
				Float.parseFloat(stringList[THREE]), Float.parseFloat(stringList[FOUR]),
				Float.parseFloat(stringList[FIVE])));
    }
    
    /**
     * Process a message about an update.
     * @param message the message to process
     */
    private void updateMessage(String message) {
    	String message2 = message.trim();
    	if (message2.equals("CIRCLELIST")) {
    		gameState.setCircleList(new ArrayList<BouncingCircle>());
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
    	} else if (message2.startsWith("LASER")) {
    		newLaserMessage(message2.replaceFirst("LASER", ""));
    	}
    }
    
    /**
     * Process a message about a laser.
     * @param message the message to process
     */
    private void newLaserMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	
    	int id = Integer.parseInt(stringList[0]);
    //	System.out.println("PLAYERID" + id);
    	Weapon weapon = new Weapon(Float.parseFloat(stringList[1]), 
    			Float.parseFloat(stringList[2]), Float.parseFloat(stringList[THREE]), 
    			Float.parseFloat(stringList[FOUR]));
    	
    	gameState.getWeaponList().setWeapon(id, weapon);
    	mainGame.getPlayerList().getPlayers().get(id).setShot(true);
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
    	
    	mainGame.getPlayerList().getPlayers().get(id).setX(x);
    	mainGame.getPlayerList().getPlayers().get(id).setY(y);
    }
    
    /**
     * Process a message about the system.
     * @param message the message to process
     */
    private void systemMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith("LEVEL")) {
    		levelMessage(message2.replaceFirst("LEVEL", ""));
    	}
    }
    
    /**
     * Process a message about the level.
     * @param message the message to process
     */
    private void levelMessage(String message) {
    	String message2 = message.trim();
    	if (message2.equals("STARTED")) {
    		gameState.setLevelStarted(true);
    	}
    }

    /**
     * Attempt to gracefully shut down the LAN threads.
     * @throws InterruptedException Thrown if shutdown takes longer than expected.
     */
    public void shutdown() throws InterruptedException {
        logger.log("Shutting down client connection", Logger.PriorityLevels.LOW, "lan");
        try {
            socket.close();
        } catch (IOException er) {
            logger.log(er.getMessage(), Logger.PriorityLevels.LOW, "lan");
        }
    }

    /**
     * Send a message to the host.
     * @param toWrite The message to send
     */ 
    public void sendMessageToHost(String toWrite) {
        this.messageQueue.add(toWrite);
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
    	if (stringList[2].equals("SHIELD")) {
    		gameState.getDroppedPowerups().add(new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.SHIELD)); // shield added to level
    	} else if (stringList[2].equals("SPIKY")) {
    		gameState.getDroppedPowerups().add(new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.SPIKY)); // spiky added to level
    	} else if (stringList[2].equals("INSTANT")) {
    		gameState.getDroppedPowerups().add(new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.INSTANT)); // inst added to level
    	}
	}

	/**
     * Remove a powerup from the level, and give it to the host player.
     * @param stringList information on powerup
     */
    private void dictatePowerup(String[] stringList) {
    	ArrayList<Powerup> poweruplist = new ArrayList<Powerup>();
		for (Powerup powerup : gameState.getDroppedPowerups()) {
			if (powerup.getxId() == Float.parseFloat(stringList[0])
					&& powerup.getyId() == Float.parseFloat(stringList[1])) {
				poweruplist.add(powerup);
				gameState.getFloatingScores().add(new FloatingScore(powerup));
				if (stringList[2].equals("SHIELD")) {
					mainGame.getPlayerList().getPlayers()
					.get(0).addPowerup(PowerupType.SHIELD); // host player given shield
				} else if (stringList[2].equals("SPIKY")) {
					mainGame.getPlayerList().getPlayers()
					.get(0).addPowerup(PowerupType.SPIKY); // host player given spiky
				} else if (stringList[2].equals("INSTANT")) {
					mainGame.getPlayerList().getPlayers()
					.get(0).addPowerup(PowerupType.INSTANT); // host player given instant
				}
			}
		}
		gameState.getDroppedPowerups().removeAll(poweruplist);
	}
    
    /**
     * Grant a powerup to the client's player.
     * @param stringList the IDs of the powerups
     */
    private void grantPowerup(String[] stringList) {
    	if (stringList[2].equals("SHIELD")) {
    		mainGame.getPlayerList().getPlayers()
    		.get(1).addPowerup(PowerupType.SHIELD); // client player given shield
    	} else if (stringList[2].equals("SPIKY")) {
    		mainGame.getPlayerList().getPlayers()
    		.get(1).addPowerup(PowerupType.SPIKY); // client player given spiky
    	} else if (stringList[2].equals("INSTANT")) {
    		mainGame.getPlayerList().getPlayers()
    		.get(1).addPowerup(PowerupType.INSTANT); // client player given instant
    	}
    	ArrayList<Powerup> poweruplist = new ArrayList<Powerup>();
		for (Powerup powerup : gameState.getDroppedPowerups()) {
			if (powerup.getxId() == Float.parseFloat(stringList[0])
					&& powerup.getyId() == Float.parseFloat(stringList[1])) {
				poweruplist.add(powerup);
				gameState.getFloatingScores().add(new FloatingScore(powerup));
			}
		}
		gameState.getDroppedPowerups().removeAll(poweruplist);
	}
    
    /**
     * Ask to use a powerup on the client's player.
     * @param powerup the powerup to confirm
     */
    public void pleaPowerup(Powerup powerup) {
    	sendMessageToHost(powerup.toString() + "PLEA ");
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
    	gameState.getDroppedCoins().add(new Coin(Float.parseFloat(stringList[0]),
				Float.parseFloat(stringList[1]), Boolean.parseBoolean(stringList[2])));
    }
    
    /**
     * Dictate that a coin goes to the host player.
     * @param stringList description of the coin
     */
    private void dictateCoin(String[] stringList) {
    	ArrayList<Coin> coinlist = new ArrayList<Coin>();
		for (Coin coin : gameState.getDroppedCoins()) {
			if (coin.getxId() == Float.parseFloat(stringList[0])
					&& coin.getyId() == Float.parseFloat(stringList[1])) {
				coinlist.add(coin);
				gameState.getFloatingScores().add(new FloatingScore(coin));
			}
		}
		gameState.getDroppedCoins().removeAll(coinlist);
    }
    
    /**
     * Grant a coin to a player.
     * @param stringList the IDs of the coins
     */
    private void grantCoin(String[] stringList) {
    	ArrayList<Coin> machvise = new ArrayList<Coin>();
		for (Coin george : gameState.getDroppedCoins()) {
			if (george.getxId() == Float.parseFloat(stringList[0])
					&& george.getyId() == Float.parseFloat(stringList[1])) {
				machvise.add(george);
				gameState.getFloatingScores().add(new FloatingScore(george));
			}
		}
		gameState.getDroppedCoins().removeAll(machvise);
	}
    
    /**
     * Confirm a coin to the host.
     * @param coin the coin to confirm
     */
    public void pleaCoin(Coin coin) {
    	sendMessageToHost(coin.toString() + "PLEA ");
    }
    
    /**
<<<<<<< HEAD
     * Notify the host that you are dead.
=======
     * javadoc.
     * @param id .
     * @param x .
     * @param y .
     * @param laserSpeed .
     * @param laserWidth .
     */
    public void updateLaser(int id, float x, float y, float laserSpeed, float laserWidth) {
    	sendMessageToHost("NEW LASER " 
    			+ id + " " + x + " " + y + " " + laserSpeed + " " + laserWidth);
    }
    
    /**
     * javadoc.
>>>>>>> master
     */
    public void updateClientDead() {
    	sendMessageToHost("PLAYER DEAD CLIENT");
    }
    

    /**
     * Set the logger for this host.
     * @param logger the logger
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * @return Whether or not the client has connected to the remote server
     */
    public boolean connectedToServer() {
        return this.socket.isConnected();
    }
}
