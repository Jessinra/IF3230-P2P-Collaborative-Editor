package Backend.P2PServer;

import Backend.CRDT.CRDTChar;
import Backend.CRDT.CRDTLog;
import Backend.UI.IEditorCallback;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * A node in a peer to peer network.
 * It composes two main sub-components: inBound and outBound.
 * It also implements the message callback interface to enable notification of incoming messages.
 */
public class Node implements IMessageCallback {

    private static final List<Integer> ASSIGNED_PORTS = new ArrayList<>();

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
        this.inBoundPort = selectPortNumber();

        try{
            InetAddress localhost = InetAddress.getLocalHost();
            this.ipAddress = localhost.getHostAddress().trim();
            System.out.println("IP Address: " + this.ipAddress);
            System.out.println("Port: " + this.inBoundPort);
        }
        catch(UnknownHostException e){
            System.out.println("Error on Node Constructor: " + e.getMessage());
            System.out.println("Stack Trace: ");
            e.printStackTrace();
        }

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

    public void broadcastLocalInsert(CRDTChar updateChar) {
        CRDTLog remoteUpdateMessage = new CRDTLog(updateChar, CRDTLog.INSERT);
        Message updateMessage = new Message(this.nodeId, remoteUpdateMessage);
        broadcastMessage(updateMessage);
    }

    public void broadcastLocalDelete(CRDTChar updateChar) {
        CRDTLog remoteUpdateMessage = new CRDTLog(updateChar, CRDTLog.DELETE);
        Message updateMessage = new Message(this.nodeId, remoteUpdateMessage);
        broadcastMessage(updateMessage);
    }

    public void broadcastMessage(Message msg) {
        for (Peer peer : peerList) {
            System.out.println(nodeId + " is broadcasting message to " + peer.getNodeId());
            this.outBound.send(peer.getIpAddr(), peer.getInboundPort(), msg);
        }
    }

    /**
     * Sends a message to a destination
     * @param msg The message to be sent
     */
    public void sendMessage(Message msg, Peer target) {
        System.out.println(this.nodeId + " is sending message to " + target.getNodeId());
        this.outBound.send(target.getIpAddr(), target.getInboundPort(), msg);
    }

    /**
     * Sends a message to a destination
     * @param msg The message to be sent
     */
    public void sendMessageToAllPeer(Message msg) {
        for(Peer peer : peerList) {
            //System.out.println(nodeId + " is sending message to " + peer.getNodeId());
            this.outBound.send(peer.getIpAddr(), peer.getInboundPort(), msg);
        }
    }

    // TODO: Butuh local update yang mangggil sendrequest., interface buat ngirim message berisi CRDT Log
    // TODO: onPeerJoin()
    // TODO: onLocalUpdate()
    // TODO: sendOnMessageReceived()
    // 2 2nya manggil sendMessageToAllPeer

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

    public String getIpAddress() {
        return ipAddress;
    }

    public ArrayList<Peer> getPeerList() {
        return peerList;
    }

    public int getPeerCount() {
        return peerList.size();
    }

    public void sendJoinRequest(String targetIpAddress, int targetPort) {
        Peer requestingPeer = new ConnectingPeer(this.ipAddress, this.nodeId, this.inBoundPort);
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


    /*
        public void onPeerConnectionReceived(Peer incomingPeer) {

        // Check for duplicate
        if (peerList.contains(incomingPeer)) {
            return;
        }

        peerList.add(incomingPeer);
        editorCallback.onPeerJoined(incomingPeer);
    */
    @Override
    public void onPeerConnectionReceived(ConnectingPeer incomingPeer) {
        Message newPeer = new Message(nodeId, new NewPeer(incomingPeer.getIpAddr(), incomingPeer.getNodeId(), incomingPeer.getInboundPort()));
        // Kirim peer baru yang connect ke semua peer dari node ini
        sendMessageToAllPeer(newPeer);
        // Tambahin peer baru sebagai peer dari node ini
        System.out.println(nodeId + " ---> " + incomingPeer.getNodeId());
        peerList.add(incomingPeer);
        Message sendPeer = new Message(nodeId, new Peer(ipAddress, nodeId, inBoundPort));
        outBound.send(incomingPeer.getIpAddr(), incomingPeer.getInboundPort(), sendPeer);
    }

    @Override
    public void onNewPeerAccepted(NewPeer newPeer) {
        System.out.println(nodeId + " ---> " + newPeer.getNodeId());
        peerList.add(newPeer);
        // Send response peer
        Message resPeer = new Message(nodeId, new Peer(ipAddress, nodeId, inBoundPort));
        outBound.send(newPeer.getIpAddr(), newPeer.getInboundPort(), resPeer);

    }

    @Override
    public void onPeerReceived(Peer peer) {
        System.out.println(nodeId + " ---> " + peer.getNodeId());
        peerList.add(peer);
    }

    public static int selectPortNumber() {
        int portNumber; //range is from 49152 -> 65532
        do {
            portNumber = 49152 + (int) (Math.random() * 16381);
        } while (ASSIGNED_PORTS.contains(portNumber));
        ASSIGNED_PORTS.add(portNumber);
        return portNumber;
    }
}
