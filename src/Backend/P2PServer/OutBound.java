package Backend.P2PServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Sub-module to be composed in a Node object for handling out-going message operations
 */
class OutBound {

    /**
     * Sends a message to a target node.
     * An encoder is used to encode the message into a string object. This enables a straightforward conversion to bytes
     * for transmission in the network.
     *
     * @param dest_addr The address of the targeted node
     * @param port      The port number of the targeted node
     * @param message   The message to be delivered
     */
    void send(String dest_addr, int port, Message message) {

        try (Socket socket = new Socket(dest_addr, port)) {

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
