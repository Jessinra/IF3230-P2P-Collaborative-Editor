package Backend.P2PServer;

import Backend.CRDT.CRDTLog;

import java.util.ArrayList;

/**
 * A node in a peer to peer network.
 * It composes two main sub-components: inBound and outBound.
 * It also implements the message callback interface to enable notification of incoming messages.
 */
public class Node implements IMessageCallback {
    /**
     * The inBound sub-component
     */
    private InBound inBound;

    /**
     * The outBound sub-component
     */
    private OutBound outBound;

    /**
     * The identification of a node in the network
     */
    private final String nodeId;

    /**
     * The port for listening for in-coming messages
     */
    private final int inBoundPort;

    /**
     * Arraylist of peers connected to this node
     */
    private ArrayList<Peer> peerList = new ArrayList<>();

    private String ipAddress;

    /**
     * Instantiates a node object
     * @param inBoundPort The listening port
     * @param nodeId The identity of the node
     */
    public Node(String ipAddress, int inBoundPort, String nodeId) {
        this.inBound = new InBound(inBoundPort, this);
        this.outBound = new OutBound();
        this.nodeId = nodeId;
        this.inBoundPort = inBoundPort;
        this.ipAddress = ipAddress;
    }

    public Node(int inBoundPort, String nodeId) {
        this.inBound = new InBound(inBoundPort, this);
        this.outBound = new OutBound();
        this.nodeId = nodeId;
        this.inBoundPort = inBoundPort;
        // Cari IP Address komputer ini
        //this.ipAddress =
    }

    /**
     * Puts the node in online mode.
     */
    public void start() {
        this.inBound.open();
    }

    /**
     * Puts the node in an offline mode
     */
    public void stop() {
        this.inBound.setIsOnline(false);
    }


    /**
     * Sends a message to a destination
     * @param msg The message to be sent
     * @param dest_addr The destination address (e.g. IPv4 address)
     * @param port The destination port number
     */
    public void sendMessage(Message msg, String dest_addr, int port) {
        for(Peer peer : peerList) {
            System.out.println(nodeId + " is sending message to " + msg.getDestinationId());
            this.outBound.send(peer.getIpAddr(), peer.getInboundPort(), msg);
        }
    }

    // TODO: Butuh local update yang mangggil sendrequest., interface buat ngirim message berisi CRDT Log
    // TODO: onPeerJoin()
    // TODO: onLocalUpdate()
    // TODO: sendOnMessageReceived()
    // 2 2nya manggil sendMessage

    /**
     * Gets the ID of the node
     * @return The node ID
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Gets the listening port number
     * @return The port number
     */
    public int getInBoundPort() {
        return inBoundPort;
    }

    private void sendJoinRequest(String ipAddress, int port) {
        Peer requestingPeer = new Peer(ipAddress, nodeId, inBoundPort);
        Message connectMessage = new Message(nodeId, requestingPeer);
        outBound.send(ipAddress, port, connectMessage);
    }

    @Override
    public void onMessageReceived(CRDTLog crdtLog) {
        // see properties of CRDT Log
        System.out.println("Operation: " + crdtLog.getOperation());
        System.out.println("CRDT Char");
        System.out.println("Position : " + crdtLog.getUpdate().getPosition());
        System.out.println("Value    : " + crdtLog.getUpdate().getValue());
        System.out.println("WriterId : " + crdtLog.getUpdate().getWriterId());
        System.out.println("Timestamp: " + crdtLog.getUpdate().getTimeStamp());
        System.out.println("Local    : " + crdtLog.isUpdatedLocally());
        // ada callback yang terima CRDT Log
    }

    @Override
    public void onPeerConnectionReceived(Peer incomingPeer) {
        peerList.add(incomingPeer);
    }
}
