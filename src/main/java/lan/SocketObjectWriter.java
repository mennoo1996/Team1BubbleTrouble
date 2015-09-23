package lan;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

/**
 * Created by alexandergeenen on 22/09/15.
 */
class SocketObjectWriter implements Runnable {

    private SocketChannel client;
    private Object item;

    /**
     * Initialize Object Writer for a Socket.
     * @param client Provided Socket Stream Channel
     * @param item Object to Stream
     */
    public SocketObjectWriter(SocketChannel client, Object item) {
        this.client = client;
        this.item = item;
    }

    @Override
    public void run() {
        try {
            if (client != null) {
                ObjectOutputStream output = new ObjectOutputStream(
                        client.socket().getOutputStream());
                output.writeObject(item);
                output.close();
            }
        } catch (SocketException ec) {
            ec.printStackTrace();
        } catch (IOException ec) {
            ec.printStackTrace();
        }
    }
}
