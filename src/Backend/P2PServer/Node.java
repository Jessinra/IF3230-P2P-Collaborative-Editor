package Backend.P2PServer;

import Backend.CRDT.CRDTChar;
import Backend.CRDT.CRDTController;
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

    private InBound inBound;
    private OutBound outBound;

    private final String nodeId;
    private String ipAddress;
    private final int inBoundPort;

    private ArrayList<Peer> peerList = new ArrayList<>();

    private IEditorCallback editorCallback;


    /**
     * Instantiates a node object
     *
     * @param nodeId         The identity of the node
     * @param inBoundPort    The listening port
     * @param editorCallback callback function from text editor
     */
    public Node(String nodeId, String ipAddress, int inBoundPort, IEditorCallback editorCallback) {

        this.nodeId = nodeId;
        this.ipAddress = ipAddress;
        this.inBoundPort = inBoundPort;

        this.inBound = new InBound(inBoundPort, this);
        this.outBound = new OutBound();

        this.peerList.add(new Peer(this.ipAddress, this.nodeId, this.inBoundPort));

        this.editorCallback = editorCallback;
    }

    public Node(String nodeId, IEditorCallback editorCallback) {
        this.nodeId = nodeId;
        this.inBoundPort = selectPortNumber();

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            this.ipAddress = localhost.getHostAddress().trim();

            System.out.println("IP Address: " + this.ipAddress);
            System.out.println("Port: " + this.inBoundPort);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        this.inBound = new InBound(this.inBoundPort, this);
        this.outBound = new OutBound();

        this.peerList.add(new Peer(this.ipAddress, this.nodeId, this.inBoundPort));

        this.editorCallback = editorCallback;
    }

    /**
     * Puts the node in online mode.
     */
    public void start() {
        this.inBound.open();
    }

    private static int selectPortNumber() {

        int portNumber; //range is from 49152 -> 65532

        do {
            portNumber = 49152 + (int) (Math.random() * 16381);
        } while (ASSIGNED_PORTS.contains(portNumber));

        ASSIGNED_PORTS.add(portNumber);
        return portNumber;
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

    public void broadcastMessage(Message message) {
        for (Peer peer : peerList) {
            sendMessage(message, peer);
        }
    }

    public void sendMessage(Message msg, Peer target) {
        this.outBound.send(target.getIpAddr(), target.getInboundPort(), msg);
    }

    public ArrayList<Peer> getPeerList() {
        return peerList;
    }

    public void addUniquePeer(Peer peer) {
        peerList.add(peer);
    }

    @Override
    public void onMessageReceived(CRDTLog crdtLog) {
        editorCallback.onRemoteUpdate(crdtLog);
    }

    public void sendJoinRequest(String targetIpAddress, int targetPort) {

        Peer requestingPeer = new Peer(this.ipAddress, this.nodeId, this.inBoundPort);
        Peer targetPeer = new Peer(targetIpAddress, "", targetPort);
        Message connectMessage = new Message(this.nodeId, requestingPeer);

        sendMessage(connectMessage, targetPeer);
    }

    @Override
    public void onPeerConnectionReceived(Peer incomingPeer) {

        if (peerList.contains(incomingPeer)) {
            return;
        }

        editorCallback.onPeerJoined(incomingPeer);
    }

    @Override
    public void onSnapshotReceived(CRDTController snapshot) {
        snapshot.setClientId(this.nodeId);
        editorCallback.onSyncSnapshot(snapshot);
    }
}
