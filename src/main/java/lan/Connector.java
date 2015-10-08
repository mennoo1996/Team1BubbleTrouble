package lan;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import powerups.Spiky;
import gui.GameState;
import gui.MainGame;
import logic.BouncingCircle;
import logic.FloatingScore;
import logic.Logger;
import logic.Player.Movement;
import logic.Weapon;

/**
 * Base class for a lan connnector.
 * @author Menno
 *
 */
public abstract class Connector implements Runnable {
	
	protected int portNumber;
	protected Queue<String> messageQueue;
	protected PrintWriter writer;
	protected BufferedReader reader;
	protected MainGame mainGame;
	protected GameState gameState;
	protected boolean heartBeatCheck;
	protected long timeLastInput;
	protected boolean running;
	protected Logger logger = Logger.getInstance();
	
	protected static final int THREE = 3;
	protected static final int FOUR = 4;
	protected static final int FIVE = 5;
	protected static final int SIX = 6;
	protected static final int SEVEN = 7;
	protected static final int TIMEOUT_ATTEMPT = 3000;

	/**
	 * Constructs a new Connector.
	 * @param portNumber the port number
	 * @param mainGame the main game that uses this connector
	 * @param gameState the game state to play using this connector
	 */
	public Connector(int portNumber, MainGame mainGame, GameState gameState) {
		this.portNumber = portNumber;
		this.mainGame = mainGame;
		this.gameState = gameState;
		this.messageQueue = new LinkedList<>();
		this.running = true;
	}
	
	
	 /**
     * Process message about splitted circles.
     * @param message	the message to process
     */
    protected void splitMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	
    	BouncingCircle circle = new BouncingCircle(Float.parseFloat(stringList[1]),
				Float.parseFloat(stringList[2]), Float.parseFloat(stringList[THREE]),
				Float.parseFloat(stringList[FOUR]), Float.parseFloat(stringList[FIVE]),
				Float.parseFloat(stringList[SIX]), Integer.parseInt(stringList[SEVEN]));
    	
    	gameState.getFloatingScores().add(new FloatingScore(circle));
    	
    	int index = gameState.getCircleList().getIndexForCircleWithID(
    			Integer.parseInt(stringList[SEVEN]));
    	
    	if (index >= 0) {
    		gameState.getCircleList().getCircles().set(index, circle);    
    		gameState.updateShotCirles2(circle, true);
    	}
    }
    
    /**
     * Message about lasers.
     * @param message String containing information about lasers
     */
    protected void laserMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.startsWith("DONE")) {
    		laserDoneMessage(message2.replaceFirst("DONE", ""));
    	}
    }
    
    /**
     * Message about a laser that is done.
     * @param message String containing information about the laser
     */
    protected void laserDoneMessage(String message) {
    	String message2 = message.trim();
    	
    	int id = Integer.parseInt(message2);
    	gameState.getWeaponList().getWeaponList().get(id).setVisible(false);
    }
    
    
    /**
     * Process a message about the player.
     * @param message String containing information about the Player
     */
    protected void playerMessage(String message) {
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
    protected void nameMessage(String message) {
    	String message2 = message.trim();
    	mainGame.getPlayerList().getPlayers().get(0).setPlayerName(message2);
    }
    
    
    /**
     * Process a message about the movement of a player.
     * @param message the message to process
     */
    protected void movementMessage(String message) {
    	String message2 = message.trim();
    
    	if (message2.startsWith("STARTED")) {
    		movementStarted(message2.replaceFirst("STARTED", ""));
    	} else if (message2.startsWith("STOPPED")) {
    		movementStopped(message2.replaceFirst("STOPPED", ""));
    	}
    }
    
    /**
     * Process a message about pause.
     * @param message	the message to process
     */
    protected void pauseMessage(String message) {
    	String message2 = message.trim();
    	
    	if (message2.equals("STARTED")) {
    		gameState.pauseStarted(true);
    	} else if (message2.equals("STOPPED")) {
    		gameState.pauseStopped(true);
    	}
    }

    /**
     * Process a message about a movement that stopped.
     * @param message the message
     */
    protected void movementStopped(String message) {
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
     * Process a started movement.
     * @param message the message to process
     */
    protected void movementStarted(String message) {
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
     * Send a message about a player that started moving.
     * @param x the x position of the player
     * @param y the y position of the player
     * @param playerNumber the player number
     * @param direction the direction in which the player started moving
     */
    public void playerStartedMoving(float x, float y, int playerNumber, String direction) {
    	String message = "PLAYER MOVEMENT STARTED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y  + " " + direction;
    	sendMessage(message);
    }
    

    /**
     * Send a message about a player that stopped moving.
     * @param x the new x position of the player
     * @param y the new y position of the player
     * @param playerNumber the player number
     */
    public void playerStoppedMoving(float x, float y, int playerNumber) {
    	String message = "PLAYER MOVEMENT STOPPED ";
    	message = message + playerNumber + " " + x + " " 
    			+ y;
    	sendMessage(message);
    }
    

    /**
     * Process a message regarding the weapon of the client.
     * @param message String that contains the information of the message
     */
    protected void newLaserMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	
    	int id = Integer.parseInt(stringList[0]);
    	boolean spikey = Boolean.parseBoolean(stringList[FIVE]);
    	System.out.println("spiky = " + spikey);
    	Weapon weapon;
    	
    	if (!spikey) {
    		weapon = new Weapon(Float.parseFloat(stringList[1]), 
        			Float.parseFloat(stringList[2]), Float.parseFloat(stringList[THREE]), 
        			Float.parseFloat(stringList[FOUR]));
    	} else {
    		weapon = new Spiky(Float.parseFloat(stringList[1]), 
        			Float.parseFloat(stringList[2]), Float.parseFloat(stringList[THREE]), 
        			Float.parseFloat(stringList[FOUR]));
    	}
    	
    	gameState.getWeaponList().setWeapon(id, weapon);
    	mainGame.getPlayerList().getPlayers().get(id).setShot(true);
    }
    
    /**
     * Update the laser on the client.
     * @param id the id of the laser
     * @param x the new x position
     * @param y the new y position
     * @param laserSpeed the new speed
     * @param laserWidth the new width
     * @param spikey .
     */
    public void updateLaser(int id, float x, float y, float laserSpeed, 
    		float laserWidth, boolean spikey) {
    	sendMessage("NEW LASER " 
    			+ id + " " + x + " " + y + " " + laserSpeed + " " + laserWidth + " " + spikey);
    }
    
    /**
     * notify client of splitted circle.
     * @param circle the splitted circle
     */
    public void splittedCircle(BouncingCircle circle) {
    	sendMessage("SPLIT " + circle.toString());
    }

    /**
     * Notify the client/host that the game has been paused.
     */
    public void updatePauseStarted() {
    	sendMessage("SYSTEM PAUSE STARTED");
    }
    
    /**
     * Notify the client/host that the game has been resumed.
     */
    public void updatePauseStopped() {
    	sendMessage("SYSTEM PAUSE STOPPED");
    }
    
    /**
     * Notify the client/host that the game will be restarted.
     */
    public void updateRestart() {
    	sendMessage("SYSTEM LEVEL RESTART");
    }
    
    /**
     * Send to client when a laser/weapon is done.
     * @param id	the id of the weapon
     */
    public void laserDone(int id) {
    	sendMessage("LASER DONE " + id);
    }

    
    /**
     * Send a message.
     * @param toWrite The string to send
     */
    public void sendMessage(String toWrite) {
        this.messageQueue.add(toWrite);
    }
   
	/**
	 * @return the portNumber
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * @param portNumber the portNumber to set
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * @return the reader
	 */
	public BufferedReader getReader() {
		return reader;
	}

	/**
	 * @param reader the reader to set
	 */
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}
	
    /**
     * Process a death message.
     * @param message the message to process
     */
    public void deadMessage(String message) {
    	mainGame.getPlayerList().playerDeath(mainGame);
    }
    
    /**
     * Inform the other client/host that a death has occurred.
     */
    public void updateDead() {
    	sendMessage("PLAYER DEAD");
    }

	/**
	 * Run method for thread.
	 */
	public abstract void run();
}
