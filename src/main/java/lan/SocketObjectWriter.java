package lan;

import java.io.IOException;
import java.net.SocketException;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by alexandergeenen on 22/09/15.
 */
class SocketObjectWriter implements Runnable {

    private SocketChannel client;
    private String item;
    private CharsetEncoder encoder;

    /**
     * Initialize Object Writer for a Socket.
     * @param client Provided Socket Stream Channel
     * @param item Object to Stream
     */
    public SocketObjectWriter(SocketChannel client, String item) {
        this.client = client;
        this.item = item;
        this.encoder = Charset.forName("US-ASCII").newEncoder();
    }

    @Override
    public void run() {
        try {
            if (client != null) {
                client.write(encoder.encode(CharBuffer.wrap(item)));
            }
        } catch (SocketException ec) {
            ec.printStackTrace();
        } catch (IOException ec) {
            ec.printStackTrace();
        }
    }
}
