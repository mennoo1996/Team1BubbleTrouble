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
    private ArrayList<Client> clientList;
    
    private static final int THREE = 3;
    private boolean heartBeatCheck;
    private long timeLastInput;

    private static final int TIMEOUT_ATTEMPT = 3000;
    private static final int MENU_MULTIPLAYER_STATE = 4;

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
    public void run()  {
        try {
            System.out.println("host.call");
            serverSocket = new ServerSocket(portNumber);
            client = serverSocket.accept();
            noClientYet = false;
            mainGame.setSwitchState(mainGame.getGameState());
            writer = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Client connected");
            timeLastInput = System.currentTimeMillis();

            // This continues ad infinitum
            while (true) {
                manageHeartbeatCheck();
                if (!messageQueue.isEmpty()) {
                    writer.println(this.messageQueue.poll());
                }
                readClientInputs();
            }
        } catch (IOException err) {
            System.out.println(err);
            System.out.println(err.getLocalizedMessage());
            this.mainGame.setSwitchState(MENU_MULTIPLAYER_STATE);
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
					System.out.println("wel toch");
					playerMessage(message2.replaceFirst("PLAYER", ""));
				} else if (message2.startsWith("POWERUP")) {
					powerupMessage(message2.replaceFirst("POWERUP", ""));
				} else if (message2.startsWith("COIN")) {
					coinMessage(message2.replaceFirst("POWERUP", ""));
				} else if (message2.startsWith("HEARTBEAT_CHECK")) {
                    this.sendMessageToClient("HEARTBEAT_ALIVE");
                } else if (message2.startsWith("HEARTBEAT_ALIVE")) {
                    heartBeatCheck = false;
                }
                timeLastInput = System.currentTimeMillis();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * Javadoc.
     * @param message .
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
     * 
     * @param coin .
     */
    private void updateCoinsGrant(Coin coin) {
    	sendMessageToClient(coin.toString() + "GRANT ");
	}

	/**
     * Javadoc.
     * @param message .
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
	 * Javadoc.
	 * @param powerup .
	 */
	private void updatePowerupsGrant(Powerup powerup) {
		sendMessageToClient(powerup.toString() + "GRANT ");
	}

	/**
     * javadoc.
     * @param message .
     */
    private void playerMessage(String message) {
    	String message2 = message.trim();
    	System.out.println(message2);
    	
    	if (message2.startsWith("DEAD")) {
    		deadMessage(message2.replaceFirst("DEAD", ""));
    	}
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

