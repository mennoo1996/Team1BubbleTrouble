package lan;

import java.nio.channels.SocketChannel;

/**
 * Client listener, handles receiving messages/data from server.
 * @author alexandergeenen
 */
public class ClientLANListener implements Runnable {

    private SocketChannel socketChannel;

    /**
     * Creates a client listener to handle all incoming data from the server.
     * @param socketChannel socket channel to listen to
     */
    public ClientLANListener(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        // Implement protocol here
    }
}
