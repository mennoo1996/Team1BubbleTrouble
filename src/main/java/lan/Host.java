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
        System.out.println("HOST INITIALIZED");
    }

    @Override
    public Boolean call() throws IOException {
    	System.out.println("host.call");
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(portNumber));
        serverSocketChannel.configureBlocking(false);

        exQueue = Executors.newSingleThreadExecutor();

        // This continues ad infinitum
        while (!exQueue.isShutdown()) {
        	System.out.println("host running and shit");
            if (noClientYet && serverSocketChannel.accept() != null) {
            	System.out.println("trying to connect client");
                noClientYet = false;
                System.out.println("CLIENT CONNECTED HEUH");
                listener = new HostLANListener(serverSocketChannel.accept());
            } else if (listener != null && serverSocketChannel.socket().getReceiveBufferSize() > 0) {
            	System.out.println("TRYING TO SUBMIT");
                exQueue.submit(listener);

            	System.out.println("done TRYING TO SUBMIT");
            }
        }
        System.out.println("exeting host");
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

