package Backend.P2PServer;

import Backend.CRDT.CRDTLog;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Handles all connection operations of the in-Bound sub-component.
 * It implements the {@link Runnable} interface in order to lend itself for usage in a {@link Thread}.
 * This is necessary considering the possible use case of a node having to handle multiple in-bound requests/connections.
 */
public class ConnectionHandler implements Runnable {
    protected final Socket socket;
    protected final IMessageCallback callback;

    public ConnectionHandler(Socket socket, IMessageCallback callback) {
        this.socket = socket;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) objectInputStream.readObject();
            if (message.getMessage() instanceof  CRDTLog) {
                callback.onMessageReceived((CRDTLog) message.getMessage());
            } else if (message.getMessage() instanceof Peer) {
                callback.onPeerConnectionReceived((Peer) message.getMessage());
            } else {
                throw new ClassNotFoundException();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
