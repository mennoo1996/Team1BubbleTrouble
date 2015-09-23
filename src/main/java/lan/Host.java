package lan;

import logic.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Host server for LAN multiplayer.
 * @author alexandergeenen
 */
public class Host implements Callable {

    private int portNumber;
    private ExecutorService exQueue;
    private ServerSocketChannel serverSocketChannel;
    private boolean noClientYet;
    private Logger logger;
    private HostLANListener listener;

    private static final int SHUTDOWN_TIMEOUT = 30;

    /**
     * Create a new Host server for LAN multiplayer.
     * @param portNumber Port number for multiplayer
     */
    public Host(int portNumber) {
        this.portNumber = portNumber;
        this.noClientYet = true;
    }

    @Override
    public Boolean call() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(portNumber));
        serverSocketChannel.configureBlocking(false);

        exQueue = Executors.newSingleThreadExecutor();
        logger.log("Listening", Logger.PriorityLevels.LOW, "lan");

        // This continues ad infinitum
        while (!exQueue.isShutdown()) {
            if (noClientYet && serverSocketChannel.accept() != null) {
                noClientYet = false;
                listener = new HostLANListener(serverSocketChannel.accept());
            } else if (serverSocketChannel.socket().getReceiveBufferSize() > 0) {
                exQueue.submit(listener);
            }
        }
        return true;
    }

    /**
     * Attempt to gracefully shut down the LAN threads.
     * @throws InterruptedException Thrown if shutdown takes longer than expected.
     */
    public void shutdown() throws InterruptedException {
        logger.log("Shutting down host server", Logger.PriorityLevels.LOW, "lan");
        exQueue.shutdown();
        exQueue.awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        exQueue.shutdownNow();
    }

    /**
     * Send a message to the client.
     * @param toWrite The string to send
     */
    public void sendMessageToClient(String toWrite) {
        if (noClientYet) {
            logger.log("Error: no client yet", Logger.PriorityLevels.LOW, "lan");
            return;
        }

        try {
            SocketObjectWriter writer = new SocketObjectWriter(serverSocketChannel.accept(),
                    toWrite);
            exQueue.submit(writer);
        } catch (IOException er) {
            er.printStackTrace();
        }
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

