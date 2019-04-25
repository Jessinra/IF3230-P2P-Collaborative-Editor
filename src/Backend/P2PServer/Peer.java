package Backend.P2PServer;

import java.io.Serializable;

public class Peer implements Serializable {
    private String ipAddr;
    private String nodeId;
    private int inboundPort;

    public Peer(String ipAddr, String nodeId, int inboundPort) {
        this.ipAddr = ipAddr;
        this.nodeId = nodeId;
        this.inboundPort = inboundPort;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getInboundPort() {
        return inboundPort;
    }

    public void setInboundPort(int inboundPort) {
        this.inboundPort = inboundPort;
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
