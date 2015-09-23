package lan;

import java.nio.channels.SocketChannel;

/**
 * Host listener, handles receiving messages/data from client.
 * @author alexandergeenen
 */
public class HostLANListener implements Runnable {

    private SocketChannel socketChannel;

    /**
     * Creates a host listener to handle all incoming data from client.
     * @param socketChannel socket channel to listen to
     */
    public HostLANListener(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        // Implement protocol here
    }
}
