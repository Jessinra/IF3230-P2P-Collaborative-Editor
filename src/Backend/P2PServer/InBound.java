package Backend.P2PServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class InBound {

    private int port;
    private boolean isOnline;
    private IMessageCallback callback;

    InBound(int port, IMessageCallback callback) {
        this.port = port;
        this.callback = callback;
        this.isOnline = true;
    }

    void open() {

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Started inbound socket on port " + port);

            while (isOnline) {
                Socket socket = serverSocket.accept();
                System.out.println("inbound: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                Runnable runnable = new ConnectionHandler(socket, callback);
                new Thread(runnable).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
