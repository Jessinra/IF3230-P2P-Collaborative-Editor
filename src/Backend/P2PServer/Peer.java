package Backend.P2PServer;

public class Peer {
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

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getInboundPort() {
        return inboundPort;
    }

    public void setInboundPort(int inboundPort) {
        this.inboundPort = inboundPort;
    }
}
