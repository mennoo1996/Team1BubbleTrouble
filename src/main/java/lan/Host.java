package lan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import gui.GameState;
import gui.MainGame;
import gui.MenuMultiplayerState;
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
public class Host extends Connector {

    private ServerSocket serverSocket;
    
    private boolean noClientYet;
    private Socket client;
    
    private static final String NO_CLIENT_CONNECTION = "No connection";
    private static final String LASER = "LASER";

    

    /**
     * Create a new Host server for LAN multiplayer.
     * @param portNumber Port number for multiplayer
     * @param mainGame the MainGame that is this host
     * @param gameState the GameState that synchronizes using this host.
     */
    public Host(int portNumber, MainGame mainGame, GameState gameState) {
        super(portNumber, mainGame, gameState);
    	this.noClientYet = true;
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
            while (running) {
                manageHeartbeatCheck();
                while (!messageQueue.isEmpty()) {
                    writer.println(this.messageQueue.poll());
                }
                readClientInputs();
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
        if (!err.getMessage().equals("Socket closed")) {
            if (err.getMessage().equals("No connection")) {
                multiplayerState.addMessage("Client disconnected");
            }
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
            throw new IOException(NO_CLIENT_CONNECTION);
        }
        if (!heartBeatCheck
                && (System.currentTimeMillis() - timeLastInput) >= TIMEOUT_ATTEMPT) {
            System.out.println("Sending heartbeat check");
            heartBeatCheck = true;
            this.messageQueue.add("HEARTBEAT_CHECK");
        }
    }

    /**
     * Work through the name message from the host.
     * @param message containing their player name.
     */
    @Override
    protected void nameMessage(String message) {
    	String message2 = message.trim();
    	mainGame.getPlayerList().getPlayers().get(1).setPlayerName(message2);
    }
    
    /**
     * Process messages received from the client.
     */
    public void readClientInputs() {
    	try {
			while (reader.ready()) {
				String message = reader.readLine();
				String message2 = message.trim();
				if (message2.startsWith("PLAYER")) {
					playerMessage(message2.replaceFirst("PLAYER", ""));
				} else if (message2.startsWith("POWERUP")) {
					powerupMessage(message2.replaceFirst("POWERUP", ""));
				} else if (message2.startsWith("COIN")) {
					coinMessage(message2.replaceFirst("COIN", ""));
				} else if (message2.startsWith("HEARTBEAT_CHECK")) {
                    this.sendMessage("HEARTBEAT_ALIVE");
                } else if (message2.startsWith("NEW")) {
					newMessage(message2.replaceFirst("NEW", ""));
				} else if (message2.startsWith("SYSTEM")) {
					systemMessage(message2.replaceFirst("SYSTEM", ""));
				} else if (message2.startsWith("SPLIT")) {
					splitMessage(message2.replaceFirst("SPLIT", ""));
				}
				readClientInputs2(message2);
                heartBeatCheck = false;
                timeLastInput = System.currentTimeMillis();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Process messages received from the client part 2.
     * @param message2 the message to process
     */
    private void readClientInputs2(String message2) {
    	if (message2.startsWith(LASER)) {
			laserMessage(message2.replaceFirst(LASER, ""));
		} else if (message2.startsWith("SHUTDOWN")) {
            MenuMultiplayerState multiplayerState = (MenuMultiplayerState)
                    this.mainGame.getState(mainGame.getMultiplayerState());
            multiplayerState.addMessage("Client Quit.");
            mainGame.setSwitchState(mainGame.getMultiplayerState());
            mainGame.killMultiplayer();
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
    	} else if (message2.startsWith("LEVEL")) {
    		levelMessage(message2.replaceFirst("LEVEL", ""));
    	}
    }
    
    /**
     * Process a message about the level.
     * @param message the message to process
     */
    private void levelMessage(String message) {
    	String message2 = message.trim();
    	if (message2.equals("RESTART")) { 
    		System.out.println("HOST IS NOW RESTARTING");
    		// force override life, level, score etc. Just. In. Case. someone forgets.
    		mainGame.resetLifeCount();
    		mainGame.resetLevelCount();
    		mainGame.setScore(0);
    		mainGame.setSwitchState(mainGame.getGameState());
    	}
    }
    
    /**
     * Process a message that starts with NEW.
     * @param message String containing the message
     */
    private void newMessage(String message) {
    	String message2 = message.trim();
    	if (message2.startsWith(LASER)) {
    		newLaserMessage(message2.replaceFirst(LASER, ""));
    	}
    }
    
    
//    /**
//     * Process a dead message.
//     * @param message the message to process
//     */
//    protected void deadMessage(String message) {
//    	String message2 = message.trim();
//    	
//    	if (message2.equals("CLIENT")) {
//    		mainGame.getPlayerList().playerDeath(mainGame);
//    	}
//    }
    
    /**
     * Attempt to gracefully shut down the LAN threads.
     * @throws InterruptedException Thrown if shutdown takes longer than expected.
     */
    public void shutdown() throws InterruptedException {
        logger.log("Shutting down host server", Logger.PriorityLevels.LOW, "lan");
        if (writer != null) {
            writer.println("SHUTDOWN");
            running = false;
        }
        try {
            serverSocket.close();
        } catch (IOException er) {
            logger.log(er.getMessage(), Logger.PriorityLevels.LOW, "lan");
        }
    }

    


    /**
     * @return Whether or not the client has connected
     */
    public boolean clientConnected() {
        return !noClientYet;
    }
    
    /**
     * Sends a message to the client concerning a FloatingScore.
     * @param floating the FloatingScore that it concerns
     */
    public void sendFloatingScore(FloatingScore floating) {
    	sendMessage(floating.toString());
    }
    
    /**
     * Update the player location on the client.
     * @param id the id of the player.
     * @param x the new x position
     * @param y the new y position
     */
    public void updatePlayerLocation(int id, float x, float y) {
    	sendMessage("NEW PLAYERLOCATION " + id + " " + x + " " + y);
    }
    
    /**
     * Inform client of a player's name.
     * @param id of the player
     * @param name of the player
     */
    public void updatePlayerName(int id, String name) {
    	sendMessage("PLAYER NAME " + id + " " + name);
    }
    

    
	/**
	 * Update the circles on the client.
	 * @param circleList the list with new circles
     */
    public void updateCircles(ArrayList<BouncingCircle> circleList) {
    	sendMessage(BouncingCircle.circleListToString(circleList));
    }
    
    /**
     * Update the requiredforgates list on the client.
     * @param circleList	the circles to update
     * @param gateNumber	the number of the gate
     */
    public void updateRequiredForGateList(ArrayList<BouncingCircle> circleList, int gateNumber) {
    	sendMessage(BouncingCircle.requiredListToString(circleList, gateNumber));
    }
    
    
    /**
     * Process an incoming powerup message.
     * @param message the message to process
     */
	private void powerupMessage(String message) {
		String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	if (stringList[THREE].equals("PLEA")) {
    		ArrayList<Powerup> poweruplist = new ArrayList<Powerup>();
    		for (Powerup powerup : gameState.getDroppedPowerups()) {
    			if (powerup.getxId() == Float.parseFloat(stringList[0])
    					&& powerup.getyId() == Float.parseFloat(stringList[1])) {
    				poweruplist.add(powerup);
    				this.updatePowerupsGrant(powerup);
    				synchronized (gameState.getFloatingScores()) {
    					gameState.getFloatingScores().add(new FloatingScore(powerup));
    				}
    				powerupMessage2(stringList);
    			}
    		} //end of loop
    		gameState.getDroppedPowerups().removeAll(poweruplist);
    	} else if (stringList[THREE].equals("ADD")) {
    		addPowerup(stringList);
    	}
	}
	
    /**
     * Add a powerup to the level on the client side.
     * @param stringList information on powerup
     */
    private void addPowerup(String[] stringList) {
    	Powerup powerup;
    	if (stringList[2].equals("SHIELD")) {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.SHIELD); // shield added to level
    	} else if (stringList[2].equals("SPIKY")) {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.SPIKY); // spiky added to level
    	} else if (stringList[2].equals("INSTANT")) {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.INSTANT); // inst added to level
    	} else if (stringList[2].equals("HEALTH")) {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.HEALTH); // health added to level
    	} else if (stringList[2].equals("FREEZE")) {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.FREEZE); // freeze added to level
    	} else if (stringList[2].equals("SLOW")) {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.SLOW); // slow added to level
    	} else if (stringList[2].equals("FAST")) {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.FAST); // fast added to level
    	} else {
    		powerup = new Powerup(Float.parseFloat(stringList[0]),
    				Float.parseFloat(stringList[1]), PowerupType.RANDOM); }
    	synchronized (gameState.getDroppedPowerups()) {
        	gameState.getDroppedPowerups().add(powerup);
    		updatePowerupsAdd(powerup); }
	}
    
	/**
	 * Extension of powerupMessage() to circumvent checkstyle.
	 * @param stringList given in powerupMessage().
	 */
	private void powerupMessage2(String[] stringList) {
		if (stringList[2].equals("SHIELD")) {
			mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SHIELD);
		} else if (stringList[2].equals("SPIKY")) {
			mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SPIKY);
		} else if (stringList[2].equals("INSTANT")) {
			mainGame.getPlayerList().getPlayers()
			.get(1).addPowerup(PowerupType.INSTANT);
		} else if (stringList[2].equals("HEALTH")) {
			mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.HEALTH);
		} else if (stringList[2].equals("FREEZE")) {
			mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.FREEZE);
		} else if (stringList[2].equals("SLOW")) {
			mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.SLOW);
		} else if (stringList[2].equals("FAST")) {
			mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.FAST);
		} else if (stringList[2].equals("RANDOM")) {
			mainGame.getPlayerList().getPlayers().get(1).addPowerup(PowerupType.RANDOM);
		}
	}
    
    /**
     * Send a powerup to the client.
     * @param powerup the powerup to send
     */
    public void updatePowerupsAdd(Powerup powerup) {
    	sendMessage(powerup.toString() + "ADD ");
		System.out.println("HOST SENT: " + powerup.toString() + "ADD ");
    }
    
    /**
     *Dictate to the client a powerup goes to the host.
     * @param powerup the powerup to dictate on
     */
    public void updatePowerupsDictate(Powerup powerup) {
    	sendMessage(powerup.toString() + "DICTATE ");
		System.out.println("HOST SENT: " + powerup.toString() + "DICTATE ");
    }
    
    /**
	 * Grant a powerup.
	 * @param powerup the powerup to grant
	 */
	private void updatePowerupsGrant(Powerup powerup) {
		sendMessage(powerup.toString() + "GRANT ");
		System.out.println("HOST SENT: " + powerup.toString() + "GRANT ");
	}
    
    /**
     * Process a message about a coin.
     * @param message the message to process
     */
    private void coinMessage(String message) {
    	String message2 = message.trim();
    	String[] stringList = message2.split(" ");
    	if (stringList[THREE].equals("PLEA")) {
    		synchronized (gameState.getDroppedCoins()) {
    			ArrayList<Coin> coinlist = new ArrayList<Coin>();
        		for (Coin coin : gameState.getDroppedCoins()) {
        			if (coin.getxId() == Float.parseFloat(stringList[0])
        					&& coin.getyId() == Float.parseFloat(stringList[1])) {
        				coinlist.add(coin);
        				this.updateCoinsGrant(coin);
        				synchronized (gameState.getFloatingScores()) {
        					gameState.getFloatingScores().add(new FloatingScore(coin));
        				}
        			}
        		}
        		gameState.getDroppedCoins().removeAll(coinlist);
    		}
    	}
	}
	
    /**
     * Send a coin to the client, to add to the level.
     * @param coin the coin to send
     */
    public void updateCoinsAdd(Coin coin) {
    	sendMessage(coin.toString() + "ADD ");
    }
    
    /**
     * Dictate to the client a coin goes to the host.
     * @param coin the coin to dictate on.
     */
    public void updateCoinsDictate(Coin coin) {
    	sendMessage(coin.toString() + "DICTATE ");
    }
    
    /**
     * Grant a coin to the client.
     * @param coin the coin to grant.
     */
    private void updateCoinsGrant(Coin coin) {
    	sendMessage(coin.toString() + "GRANT ");
	}
    
    /**
     * Notify the client that the level has started.
     */
    public void updateLevelStarted() {
    	sendMessage("SYSTEM LEVEL STARTED");
    }
    
    /**
     * Notify the client that the countin has started.
     */
    public void updateCountinStarted() {
    	sendMessage("SYSTEM COUNTIN STARTED");
    }
   
    /**
     * Notify the client with new lives.
     * @param lives the lives to update
     */
    public void updateLives(int lives) {
    	sendMessage("SYSTEM LIVES " + lives);
    }

    
}

