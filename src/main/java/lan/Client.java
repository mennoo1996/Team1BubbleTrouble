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
     * @param mainGame javadoc
     * @param gameState javadoc
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
				System.out.println("received message: " + message);
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
     * javadoc.
     * @param message .
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
     * javadoc.
     * @param message .
     */
    private void deadMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.equals("HOST")) {
    		mainGame.getPlayerList().playerDeath(mainGame);
    	}
    }
    
    /**
     * javadoc.
     * @param message .
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
     * javadoc.
     * @param message .
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
     * javadoc.
     * @param message .
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
     * javadoc. 
     * @param x .
     * @param y .
     * @param playerNumber .
     * @param direction .
     */
    public void playerStartedMoving(float x, float y, int playerNumber, String direction) {
    	String message = "PLAYER MOVEMENT STARTED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y  + " " + direction;
    	sendMessageToHost(message);
    }
    
    /**
     * javadoc.
     * @param x .
     * @param y .
     * @param playerNumber .
     */
    public void playerStoppedMoving(float x, float y, int playerNumber) {
    	String message = "PLAYER MOVEMENT STOPPED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y;
    	sendMessageToHost(message);
    }
    
    /**
     * javadoc.
     * @param message .
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
     * javadoc.
     * @param message .
     */
    private void updateMessage(String message) {
    	String message2 = message.trim();
    	if (message2.equals("CIRCLELIST")) {
    		gameState.setCircleList(new ArrayList<BouncingCircle>());
    	}
    	
    }
    
    /**
     * javadoc.
     * @param message .
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
     * Remove a powerup in the list of powerups from the client.
     * @param stringList information on powerup
     */
    private void dictatePowerup(String[] stringList) {
    	ArrayList<Powerup> machvise = new ArrayList<Powerup>();
		for (Powerup george : gameState.getDroppedPowerups()) {
			if (george.getxId() == Float.parseFloat(stringList[0])
					&& george.getyId() == Float.parseFloat(stringList[1])) {
				machvise.add(george);
				gameState.getFloatingScores().add(new FloatingScore(george));
				if (stringList[2].equals("SHIELD")) {
					mainGame.getPlayerList().getPlayers().get(0).addPowerup(PowerupType.SHIELD);
				} else if (stringList[2].equals("SPIKY")) {
					mainGame.getPlayerList().getPlayers().get(0).addPowerup(PowerupType.SPIKY);
				} else if (stringList[2].equals("INSTANT")) {
					mainGame.getPlayerList().getPlayers()
					.get(0).addPowerup(PowerupType.INSTANT);
				}
			}
		}
		gameState.getDroppedPowerups().removeAll(machvise);
	}

	/**
     * Add a powerup to the list of powerups from the client.
     * @param stringList information on powerup
     */
    private void addPowerup(String[] stringList) {
    	// SHIELD, SPIKY, INSTANT
    	if (stringList[2].equals("SHIELD")) {
    		gameState.getDroppedPowerups().add(new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.SHIELD));
    	} else if (stringList[2].equals("SPIKY")) {
    		gameState.getDroppedPowerups().add(new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.SPIKY));
    	} else if (stringList[2].equals("INSTANT")) {
    		gameState.getDroppedPowerups().add(new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.INSTANT));
    	}
	}

	/**
     * javadoc.
     * @param message .
     */
    private void coinMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	if (stringList[THREE].equals("ADD")) {
    		gameState.getDroppedCoins().add(new Coin(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), Boolean.parseBoolean(stringList[2])));
    	} else if (stringList[THREE].equals("DICTATE")) {
    		ArrayList<Coin> machvise = new ArrayList<Coin>();
    		for (Coin george : gameState.getDroppedCoins()) {
    			if (george.getxId() == Float.parseFloat(stringList[0])
    					&& george.getyId() == Float.parseFloat(stringList[1])) {
    				machvise.add(george);
    				gameState.getFloatingScores().add(new FloatingScore(george));
    			}
    		}
    		gameState.getDroppedCoins().removeAll(machvise);
    	} else if (stringList[THREE].equals("GRANT")) {
    		grantCoin(stringList);
    	}
    }
    
    /**
     * 
     * @param stringList .
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
     * 
     * @param stringList .
     */
    private void grantPowerup(String[] stringList) {
    	if (stringList[2].equals("SHIELD")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SHIELD);
    	} else if (stringList[2].equals("SPIKY")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SHIELD);
    	} else if (stringList[2].equals("INSTANT")) {
    		mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SHIELD);
    	}
    	ArrayList<Powerup> machvise = new ArrayList<Powerup>();
		for (Powerup george : gameState.getDroppedPowerups()) {
			if (george.getxId() == Float.parseFloat(stringList[0])
					&& george.getyId() == Float.parseFloat(stringList[1])) {
				machvise.add(george);
				gameState.getFloatingScores().add(new FloatingScore(george));
			}
		}
		gameState.getDroppedPowerups().removeAll(machvise);
	}
    
	/**
     * javadoc.
     * @param message .
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
     * javadoc.
     * @param message .
     */
    private void newLaserMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	
    	int id = Integer.parseInt(stringList[0]);
    	System.out.println("PLAYERID" + id);
    	Weapon weapon = new Weapon(Float.parseFloat(stringList[1]), 
    			Float.parseFloat(stringList[2]), Float.parseFloat(stringList[THREE]), 
    			Float.parseFloat(stringList[FOUR]));
    	
    	gameState.getWeaponList().setWeapon(id, weapon);
    	mainGame.getPlayerList().getPlayers().get(id).setShot(true);
    }
    
    /**
     * javadoc.
     * @param message .
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
     * javadoc.
     * @param message .
     */
    private void systemMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith("LEVEL")) {
    		levelMessage(message2.replaceFirst("LEVEL", ""));
    	}
    }
    
    /**
     * javadoc.
     * @param message .
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
     * javadoc.
     * @param powerup .
     */
    public void updatePowerupsClient(Powerup powerup) {
    	sendMessageToHost(powerup.toString() + "PLEA ");
    }
    
    /**
     * javadoc.
     * @param coin .
     */
    public void updateCoinsClient(Coin coin) {
    	sendMessageToHost(coin.toString() + "PLEA ");
    }
    
    /**
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
