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
        messageQueue.add("Hi");

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
			    System.out.println(reader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
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
     * @param id .
     * @param x .
     * @param y .
     */
    public void updatePlayerLocation(int id, float x, float y) {
    	sendMessageToClient("NEW PLAYERLOCATION " + id + " " + x + " " + y);
    }
    
    /**
     * javadoc. 
     * @param x .
     * @param y .
     * @param playerNumber .
     * @param direction .
     */
    public void playerStartedMoving(float x, float y, int playerNumber, String direction) {
    	String message = "PLAYER MOVEMENT STARTED " + playerNumber + " " + x + " " 
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
    	String message = "PLAYER MOVEMENT STOPPED " + playerNumber + " " + x + " " 
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

