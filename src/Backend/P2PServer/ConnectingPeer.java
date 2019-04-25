package Backend.P2PServer;

public class ConnectingPeer extends Peer {
    public ConnectingPeer(String ipAddr, String nodeId, int inboundPort) {
        super(ipAddr, nodeId, inboundPort);
    }
}
