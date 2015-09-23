package lan;

import logic.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Client class which connects to server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Client implements Callable {

    private int portNumber;
    private String host;
    private ExecutorService exQueue;
    private SocketChannel socketChannel;
    private boolean isConnected;
    private Logger logger;
    private ClientLANListener listener;

    private static final int SHUTDOWN_TIMEOUT = 30;

    /**
     * Create a new Client connection for LAN multiplayer.
     * @param host Host server address
     * @param portNumber Port number for multiplayer
     */
    public Client(String host, int portNumber) {
        this.host = host;
        this.portNumber = portNumber;
        this.isConnected = false;
    }

    @Override
    public Boolean call() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.bind(new InetSocketAddress(portNumber));
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(host, portNumber));

        exQueue = Executors.newSingleThreadExecutor();
        logger.log("Connecting", Logger.PriorityLevels.LOW, "lan");

        // This continues ad infinitum
        while (!exQueue.isShutdown()) {
            if (this.isConnected) {
                //Connected logic
                if (socketChannel.socket().getReceiveBufferSize() > 0) {
                    exQueue.submit(listener);
                }
            } else {
                // Check if connection has succeeded
                if (socketChannel.isConnected()) {
                    this.isConnected = true;
                    this.listener = new ClientLANListener(socketChannel);
                } else {
                    // Throw error if connection didn't succeed
                    throw new IOException("Client could not connect to host server");
                }
            }
        }
        return true;
    }

    /**
     * Attempt to gracefully shut down the LAN threads.
     * @throws InterruptedException Thrown if shutdown takes longer than expected.
     */
    public void shutdown() throws InterruptedException {
        logger.log("Shutting down client connection", Logger.PriorityLevels.LOW, "lan");
        try {
            socketChannel.close();
        } catch (IOException er) {
            logger.log(er.getMessage(), Logger.PriorityLevels.LOW, "lan");
        }
        exQueue.shutdown();
        exQueue.awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        exQueue.shutdownNow();
    }

    /**
     * Send a message to the host.
     * @param toWrite The message to send
     */
    public void sendMessageToHost(String toWrite) {
        if (!isConnected) {
            logger.log("Error: no client yet", Logger.PriorityLevels.LOW, "lan");
            return;
        }

        SocketObjectWriter writer = new SocketObjectWriter(socketChannel,
                toWrite);
        exQueue.submit(writer);
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
        return this.socketChannel.isConnected();
    }
}
