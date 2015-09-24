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
import java.util.concurrent.Callable;

import logic.BouncingCircle;
import logic.Coin;
import logic.Logger;
import logic.Powerup;
import logic.Player.Movement;

/**
 * Host server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Host implements Callable {

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
    private ArrayList<Client> clientList;
    

    private static final int THREE = 3;
    
    /**
     * Create a new Host server for LAN multiplayer.
     * @param portNumber Port number for multiplayer
     * @param mainGame javaodc
     * @param gameState javado
     */
    public Host(int portNumber, MainGame mainGame, GameState gameState) {
        this.portNumber = portNumber;
        this.gameState = gameState;
        this.mainGame = mainGame;
        this.noClientYet = true;
        this.messageQueue = new LinkedList<>();
        clientList = new ArrayList<Client>();
        System.out.println("HOST INITIALIZED");
    }

    @Override
    public Boolean call() throws IOException {
    	System.out.println("host.call");
        serverSocket = new ServerSocket(portNumber);
        client = serverSocket.accept();
        noClientYet = false;
		mainGame.setSwitchState(mainGame.getGameState());
        writer = new PrintWriter(client.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("Client connected");
        // Say hello here
        messageQueue.add("Hi"); // yep like that
        messageQueue.add("PLAYER NAME " // send your player's name to client
        + mainGame.getPlayerList().getPlayers().get(0).getPlayerName());

        // This continues ad infinitum
        while (true) {
            if (!messageQueue.isEmpty()) {
                writer.println(this.messageQueue.poll());
            }
            readClientInputs();
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
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * javadoc.
     * @param message .
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
     * Work through the name message from the host.
     * @param message containing their player name.
     */
    private void nameMessage(String message) {
    	String message2 = message.trim();
    	mainGame.getPlayerList().getPlayers().get(1).setPlayerName(message2);
    }
    
    /**
     * javadoc.
     * @param message .
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
     * javadoc.
     */
    public void updateHostDead() {
    	sendMessageToClient("PLAYER DEAD HOST");
    }
    
    /**
     * javadoc.
     * @param id .
     * @param x .
     * @param y .
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
    	sendMessageToClient(message);
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
    	sendMessageToClient(message);
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
    	sendMessageToClient("NEW LASER " 
    			+ id + " " + x + " " + y + " " + laserSpeed + " " + laserWidth);
    }
    
    /**
     * javadoc.
     * @param circleList .
     */
    public void updateCircles(ArrayList<BouncingCircle> circleList) {
		sendMessageToClient("UPDATE CIRCLELIST");
		for (BouncingCircle bCircle : circleList) {
			sendMessageToClient(bCircle.toString());
		}
    }
    
    /**
     * javadoc.
     * @param a the powerup to sent
     */
    public void updatePowerups(Powerup a) {
    	sendMessageToClient(a.toString() + "ADD ");
    }
    
    /**
     * javadoc.
     * @param a the coin to sent
     */
    public void updateCoins(Coin a) {
    	sendMessageToClient(a.toString() + "ADD ");
    }
    
    /**
     * javadoc.
     * @param a the powerup to sent
     */
    public void updatePowerupsHost(Powerup a) {
    	sendMessageToClient(a.toString() + "DICTATE ");
    }
    
    /**
     * javadoc.
     * @param a the coin to sent
     */
    public void updateCoinsHost(Coin a) {
    	sendMessageToClient(a.toString() + "DICTATE ");
    }
    
    /**
     * javadoc.
     */
    public void updateLevelStarted() {
    	sendMessageToClient("SYSTEM LEVEL STARTED");
    }
}

