package lan;

import gui.GameState;
import gui.MainGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import logic.BouncingCircle;
import logic.Coin;
import logic.FloatingScore;
import logic.Logger;
import logic.Powerup;
import logic.Weapon;
import logic.Player.Movement;
import logic.Powerup.PowerupType;

/**
 * Host server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Host implements Runnable {

    private int portNumber;
    private ServerSocket serverSocket;
    private Logger logger;
    private boolean noClientYet;
    private Socket client;
    private Queue<String> messageQueue;
    private PrintWriter writer;
    private BufferedReader reader;
    private MainGame mainGame;
    private GameState gameState;

    private static final int THREE = 3;
    private boolean heartBeatCheck;
    private long timeLastInput;

    private static final int TIMEOUT_ATTEMPT = 10000;
    private static final int FOUR = 4;

    /**
     * Create a new Host server for LAN multiplayer.
     * @param portNumber Port number for multiplayer
     * @param mainGame the MainGame that is this host
     * @param gameState the GameState that synchronizes using this host.
     */
    public Host(int portNumber, MainGame mainGame, GameState gameState) {
        this.portNumber = portNumber;
        this.gameState = gameState;
        this.mainGame = mainGame;
        this.noClientYet = true;
        this.messageQueue = new LinkedList<>();
        System.out.println("HOST INITIALIZED");
    }

    @Override
    public void run()  {
        try {
            System.out.println("host.call");
            serverSocket = new ServerSocket(portNumber);
            client = serverSocket.accept();
            noClientYet = false;
            mainGame.setSwitchState(mainGame.getGameState());
            writer = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            messageQueue.add("PLAYER NAME " // send your player's name to client
                + mainGame.getPlayerList().getPlayers().get(0).getPlayerName());
            System.out.println("Client connected");
            timeLastInput = System.currentTimeMillis();

            // This continues ad infinitum
            while (true) {
                manageHeartbeatCheck();
                while (!messageQueue.isEmpty()) {
                    writer.println(this.messageQueue.poll());
                }
                readClientInputs();
            }
        } catch (IOException err) {
            System.out.println(err);
            System.out.println(err.getLocalizedMessage());
            this.mainGame.setSwitchState(mainGame.getMultiplayerState());
        }
    }

    /**
     * Triggers/ends the heartbeat check for a possible missing client.
     * @throws IOException Thrown if connection to client no longer exists
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
     * Process client inputs.
     */
    private void readClientInputs() {
    	try {
			while (reader.ready()) {

				System.out.println("in loop");
				String message = reader.readLine();
				System.out.println(message);
				String message2 = message.trim();
				System.out.println(message2);
				if (message2.startsWith("PLAYER")) {
					playerMessage(message2.replaceFirst("PLAYER", ""));
				} else if (message2.startsWith("POWERUP")) {
					powerupMessage(message2.replaceFirst("POWERUP", ""));
				} else if (message2.startsWith("COIN")) {
					coinMessage(message2.replaceFirst("POWERUP", ""));
				} else if (message2.startsWith("HEARTBEAT_CHECK")) {
                    this.sendMessageToClient("HEARTBEAT_ALIVE");
                } else if (message2.startsWith("HEARTBEAT_ALIVE")) {
                    heartBeatCheck = false;
                } else if (message2.startsWith("NEW")) {
					newMessage(message2.replaceFirst("NEW", ""));
				} else if (message2.startsWith("SYSTEM")) {
					systemMessage(message2.replaceFirst("SYSTEM", ""));
				}
                timeLastInput = System.currentTimeMillis();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Process a message about the system.
     * @param message the message to process
     */
    private void systemMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith("PAUSE")) {
    		pauseMessage(message2.replaceFirst("PAUSE", ""));
    	}
    }
    
    /**
     * Process a message about pause.
     * @param message	the message to process
     */
    private void pauseMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.equals("STARTED")) {
    		gameState.pauseStarted(true);
    	} else if (message2.equals("STOPPED")) {
    		gameState.pauseStopped(true);
    	}
    }
    
    /**
     * javadoc.
     * @param message .
     */
    private void newMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith("LASER")) {
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
     * Process a message about a coin.
     * @param message the message to process
     */
    private void coinMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	if (stringList[THREE].equals("PLEA")) {
    		ArrayList<Coin> machvise = new ArrayList<Coin>();
    		for (Coin george : gameState.getDroppedCoins()) {
    			if (george.getxId() == Float.parseFloat(stringList[0])
    					&& george.getyId() == Float.parseFloat(stringList[1])) {
    				machvise.add(george);
    				this.updateCoinsGrant(george);
    				gameState.getFloatingScores().add(new FloatingScore(george));
    			}
    		}
    		gameState.getDroppedCoins().removeAll(machvise);
    	}
	}

    /**
     * Grant a coin.
     * @param coin the coin to grant.
     */
    private void updateCoinsGrant(Coin coin) {
    	sendMessageToClient(coin.toString() + "GRANT ");
	}

	/**
     * Process a powerup message.
     * @param message the message to process
     */
	private void powerupMessage(String message) {
		String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	if (stringList[THREE].equals("PLEA")) {
    		ArrayList<Powerup> machvise = new ArrayList<Powerup>();
    		for (Powerup george : gameState.getDroppedPowerups()) {
    			if (george.getxId() == Float.parseFloat(stringList[0])
    					&& george.getyId() == Float.parseFloat(stringList[1])) {
    				machvise.add(george);
    				this.updatePowerupsGrant(george);
    				gameState.getFloatingScores().add(new FloatingScore(george));
    				if (stringList[2].equals("SHIELD")) {
    					mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SHIELD);
    				} else if (stringList[2].equals("SPIKY")) {
    					mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SPIKY);
    				} else if (stringList[2].equals("INSTANT")) {
    					mainGame.getPlayerList().getPlayers()
    					.get(1).addPowerup(PowerupType.INSTANT);
    				}
    			}
    		} //end of loop
    		gameState.getDroppedPowerups().removeAll(machvise);
    	}
	}

	/**
	 * Grant a powerup.
	 * @param powerup the powerup to grant
	 */
	private void updatePowerupsGrant(Powerup powerup) {
		sendMessageToClient(powerup.toString() + "GRANT ");
	}

	/**
     * Process a player message.
     * @param message the message to process
     */
    private void playerMessage(String message) {
    	String message2 = message.trim();
    	System.out.println(message2);
    	
    	if (message2.startsWith("MOVEMENT")) {
    		movementMessage(message2.replaceFirst("MOVEMENT", ""));
    	} else if (message2.startsWith("DEAD")) {
    		deadMessage(message2.replaceFirst("DEAD", ""));
    	} else if (message2.startsWith("NAME")) {
    		nameMessage(message2.replaceFirst("NAME", ""));
    	}
    }
    
    /**
     * Process a movement message.
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
     * Process a started movement.
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
     * Process a stopped movement.
     * @param message the message to process
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
     * Work through the name message from the host.
     * @param message containing their player name.
     */
    private void nameMessage(String message) {
    	String message2 = message.trim();
    	mainGame.getPlayerList().getPlayers().get(1).setPlayerName(message2);
    }
    
    /**
     * Process a dead message.
     * @param message the message to process
     */
    private void deadMessage(String message) {
    	String message2 = message.trim();
    	System.out.println(message2);
    	
    	if (message2.equals("CLIENT")) {
    		System.out.println("client dead");
    		mainGame.getPlayerList().playerDeath(mainGame);
    	}
    }
    

    /**
     * Attempt to gracefully shut down the LAN threads.
     * @throws InterruptedException Thrown if shutdown takes longer than expected.
     */
    public void shutdown() throws InterruptedException {
        logger.log("Shutting down host server", Logger.PriorityLevels.LOW, "lan");
        try {
            serverSocket.close();
        } catch (IOException er) {
            logger.log(er.getMessage(), Logger.PriorityLevels.LOW, "lan");
        }
    }

    /**
     * Send a message to the client.
     * @param toWrite The string to send
     */
    public void sendMessageToClient(String toWrite) {
    	System.out.println(toWrite);
        this.messageQueue.add(toWrite);
    }

    /**
     * Set the logger for this host.
     * @param logger the logger
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * @return Whether or not the client has connected
     */
    public boolean clientConnected() {
        return !noClientYet;
    }
    
    /**
     *	Notify the client that the host is dead.
     */
    public void updateHostDead() {
    	sendMessageToClient("PLAYER DEAD HOST");
    }
    
    /**
     * Update the player location on the client.
     * @param id the id of the player.
     * @param x the new x position
     * @param y the new y position
     */
    public void updatePlayerLocation(int id, float x, float y) {
    	sendMessageToClient("NEW PLAYERLOCATION " + id + " " + x + " " + y);
    }
    
    /**
     * Inform client of a player's name.
     * @param id of the player
     * @param name of the player
     */
    public void updatePlayerName(int id, String name) {
    	sendMessageToClient("PLAYER NAME " + id + " " + name);
    }
    
    /**
     * Start a player movement on the client.
     * @param x the x position of the player
     * @param y the y position of the player
     * @param playerNumber the player number
     * @param direction the direction that the player started to move in
     */
    public void playerStartedMoving(float x, float y, int playerNumber, String direction) {
    	String message = "PLAYER MOVEMENT STARTED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y  + " " + direction;
    	sendMessageToClient(message);
    }
    
    /**
     * Stop a player movement on the client.
     * @param x the new x position of the player
     * @param y the new y position of the player
     * @param playerNumber the player number
     */
    public void playerStoppedMoving(float x, float y, int playerNumber) {
    	String message = "PLAYER MOVEMENT STOPPED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y;
    	sendMessageToClient(message);
    }
    
    /**
     * Update the laser on the client.
     * @param id the id of the laser
     * @param x the new x position
     * @param y the new y position
     * @param laserSpeed the new speed
     * @param laserWidth the new width
     */
    public void updateLaser(int id, float x, float y, float laserSpeed, float laserWidth) {
    	sendMessageToClient("NEW LASER " 
    			+ id + " " + x + " " + y + " " + laserSpeed + " " + laserWidth);
    }
    

    /**
     * Send to client when a laser/weapon is done.
     * @param id	the id of the weapon
     */
    public void laserDone(int id) {
    	sendMessageToClient("LASER DONE " + id);
    }
    
	/**
	 * Update the circles on the client.
	 * @param circleList the list with new circles
     */
    public void updateCircles(ArrayList<BouncingCircle> circleList) {
    	sendMessageToClient(BouncingCircle.circleListToString(circleList));
    }
    
    /**
     * Send a powerup to the client.
     * @param a the powerup to sent
     */
    public void updatePowerups(Powerup a) {
    	sendMessageToClient(a.toString() + "ADD ");
    }
    
    /**
     * Send a coin to the client.
     * @param a the coin to sent
     */
    public void updateCoins(Coin a) {
    	sendMessageToClient(a.toString() + "ADD ");
    }
    
    /**
     * Update a powerup on the client.
     * @param a the powerup to sent
     */
    public void updatePowerupsHost(Powerup a) {
    	sendMessageToClient(a.toString() + "DICTATE ");
    }
    
    /**
     * Update a powerup on the client.
     * @param a the coin to sent
     */
    public void updateCoinsHost(Coin a) {
    	sendMessageToClient(a.toString() + "DICTATE ");
    }
    
    /**
     * Notify the client that the level has started.
     */
    public void updateLevelStarted() {
    	sendMessageToClient("SYSTEM LEVEL STARTED");
    }
    
    /**
     * Notify the client that the countin has started.
     */
    public void updateCountinStarted() {
    	sendMessageToClient("SYSTEM COUNTIN STARTED");
    }
    
    /**
     * Notify the client that the game has been paused.
     */
    public void updatePauseStarted() {
    	sendMessageToClient("SYSTEM PAUSE STARTED");
    }
    
    /**
     * Notify the client that the game has been resumed.
     */
    public void updatePauseStopped() {
    	sendMessageToClient("SYSTEM PAUSE STOPPED");
    }
    
    /**
     * Notify the client with new lives.
     * @param lives the lives to update
     */
    public void updateLives(int lives) {
    	sendMessageToClient("SYSTEM LIVES " + lives);
    }
}

