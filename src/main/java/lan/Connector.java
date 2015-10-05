package lan;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import gui.GameState;
import gui.MainGame;
import logic.Logger;

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
	protected static final int TIMEOUT_ATTEMPT = 10000;

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
	 * Run method for thread.
	 */
	public abstract void run();
	
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
	
}
