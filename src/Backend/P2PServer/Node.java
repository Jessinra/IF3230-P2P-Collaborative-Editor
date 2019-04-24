package Backend.P2PServer;

import Backend.CRDT.CRDTLog;
import Backend.UI.IEditorCallback;

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
     * This IP address for listening incoming messages
     */
    private String ipAddress;

    /**
     * The port for listening for in-coming messages
     */
    private final int inBoundPort;


    /**
     * Arraylist of peers connected to this node
     */
    private ArrayList<Peer> peerList = new ArrayList<>();

    private IEditorCallback editorCallback;


    /**
     * Instantiates a node object
     * @param nodeId The identity of the node
     * @param inBoundPort The listening port
     * @param editorCallback callback function from text editor
     */
    public Node(String nodeId, String ipAddress, int inBoundPort, IEditorCallback editorCallback) {

        this.nodeId = nodeId;
        this.ipAddress = ipAddress;
        this.inBoundPort = inBoundPort;

        this.inBound = new InBound(inBoundPort, this);
        this.outBound = new OutBound();

        this.editorCallback = editorCallback;
    }

    public Node(String nodeId, IEditorCallback editorCallback) {

        this.nodeId = nodeId;
        this.inBoundPort = 8080;
        // TODO : Cari IP Address komputer ini
        // this.ipAddress =

        this.inBound = new InBound(this.inBoundPort, this);
        this.outBound = new OutBound();

        this.editorCallback = editorCallback;
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

    public ArrayList<Peer> getPeerList() {
        return peerList;
    }

    public int getPeerCount() {
        return peerList.size();
    }

    private void sendJoinRequest(String targetIpAddress, int targetPort) {
        Peer requestingPeer = new Peer(targetIpAddress, this.nodeId, this.inBoundPort);
        Message connectMessage = new Message(this.nodeId, requestingPeer);
        outBound.send(targetIpAddress, targetPort, connectMessage);
    }

    @Override
    public void onMessageReceived(CRDTLog crdtLog) {
//        // see properties of CRDT Log
//        System.out.println("Operation: " + crdtLog.getOperation());
//        System.out.println("CRDT Char");
//        System.out.println("Position : " + crdtLog.getUpdate().getPosition());
//        System.out.println("Value    : " + crdtLog.getUpdate().getValue());
//        System.out.println("WriterId : " + crdtLog.getUpdate().getWriterId());
//        System.out.println("Timestamp: " + crdtLog.getUpdate().getTimeStamp());
//        System.out.println("Local    : " + crdtLog.isUpdatedLocally());

        editorCallback.onRemoteUpdate(crdtLog);
    }

    @Override
    public void onPeerConnectionReceived(Peer incomingPeer) {
        peerList.add(incomingPeer);
        editorCallback.onPeerJoined(incomingPeer);
    }
}
