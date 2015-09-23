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
 * Created by alexandergeenen on 22/09/15.
 */
public class Host implements Callable {

    private int portNumber;
    private ExecutorService exQueue;
    private ServerSocketChannel serverSocketChannel;
    private boolean noClientYet;
    private Logger logger;

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
        System.out.println(serverSocketChannel.isOpen());

        exQueue = Executors.newSingleThreadExecutor();
        logger.log("Listening", Logger.PriorityLevels.LOW, "lan");
        while (!exQueue.isShutdown()) {
            if (noClientYet && serverSocketChannel.accept() != null) {
                noClientYet = false;
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
     * Send an object to the client.
     * @param toWrite The object to send
     */
    public void writeObjectToClient(Object toWrite) {
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
}

