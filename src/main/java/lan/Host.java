package lan;

import logic.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Callable;

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
    private Scanner reader;

    /**
     * Create a new Host server for LAN multiplayer.
     * @param portNumber Port number for multiplayer
     */
    public Host(int portNumber) {
        this.portNumber = portNumber;
        this.noClientYet = true;
        this.messageQueue = new LinkedList<>();
        System.out.println("HOST INITIALIZED");
    }

    @Override
    public Boolean call() throws IOException {
    	System.out.println("host.call");
        serverSocket = new ServerSocket(portNumber);
        client = serverSocket.accept();
        noClientYet = false;
        writer = new PrintWriter(client.getOutputStream(), true);
        reader = new Scanner(new InputStreamReader(client.getInputStream()));
        System.out.println("Client connected");
        messageQueue.add("Hi");

        // This continues ad infinitum
        while (true) {
            if (!messageQueue.isEmpty()) {
                writer.println(this.messageQueue.poll());
                System.out.println("Sent message");
            }

            readClientInputs();
        }
    }

    /**
     * Process client inputs.
     */
    private void readClientInputs() {
        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
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
}

