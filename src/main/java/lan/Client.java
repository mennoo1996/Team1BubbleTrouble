package lan;

import logic.Logger;

import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

/**
 * Client class which connects to server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Client implements Callable {

    private int portNumber;
    private String host;
    private Socket socket;
    private boolean isConnected;
    private Logger logger;
    private Queue<String> messageQueue;
    private PrintWriter writer;
    private Scanner reader;

    /**
     * Create a new Client connection for LAN multiplayer.
     * @param host Host server address
     * @param portNumber Port number for multiplayer
     */
    public Client(String host, int portNumber) {
        this.host = host;
        this.portNumber = portNumber;
        this.isConnected = false;
        this.messageQueue = new LinkedList<>();
    }

    @Override
    public Boolean call() throws IOException {
    	System.out.println("CLIENT.call");
        socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), portNumber));
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new Scanner(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected to server");
        // This continues ad infinitum
        while (true) {
            // READ AND WRITE LOGIC HERE
            if (!this.messageQueue.isEmpty()) {
                writer.println(this.messageQueue.poll());
            }
            readServerCommands();
        }
    }

    /**
     * Process server commands.
     */
    private void readServerCommands() {
        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
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
