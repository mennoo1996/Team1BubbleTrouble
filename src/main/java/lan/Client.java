package lan;
import guigame.GameState;
import guimenu.MainGame;
import guimenu.MenuMultiplayerState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import logic.Coin;
import logic.Logger;
import powerups.Powerup;

/**
 * Client class which connects to server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Client extends Connector {

    private String host;
    private Socket socket;
    private ClientMessageReader messageReader;	

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
		messageReader = new ClientMessageReader(this, mainGame, gameState);
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
				messageReader.readServerCommands(reader);
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
	 * Reset heartbeat check.
	 */
	public void resetHeartBeat() {
		heartBeatCheck = false;
		timeLastInput = System.currentTimeMillis();
	}
    
    /**
     * Send a powerup to the host.
     * @param powerup the powerup to send
     */
    public void updatePowerupsAdd(Powerup powerup) {
    	sendMessage(powerup.toString() + "ADD ");
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
     * Ask to use a powerup on the client's player.
     * @param powerup the powerup to confirm
     */
    public void pleaPowerup(Powerup powerup) {
    	sendMessage(powerup.toString() + "PLEA ");
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
