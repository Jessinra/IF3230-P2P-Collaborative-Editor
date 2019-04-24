package Backend.P2PServer;

import java.io.Serializable;

public class Peer implements Serializable {
    private String ipAddr;
    private String nodeId;
    private int inboundPort;

    Peer(String ipAddr, String nodeId, int inboundPort) {
        this.ipAddr = ipAddr;
        this.nodeId = nodeId;
        this.inboundPort = inboundPort;
    }

    String getIpAddr() {
        return ipAddr;
    }

    int getInboundPort() {
        return inboundPort;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Peer)) {
            return false;
        }

        Peer o = (Peer) obj;
        return this.ipAddr.equals(o.ipAddr) && this.nodeId.equals(o.nodeId) && this.inboundPort == o.inboundPort;
    }
}
