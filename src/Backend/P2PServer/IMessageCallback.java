package Backend.P2PServer;

import Backend.CRDT.CRDTController;
import Backend.CRDT.CRDTLog;

/**
 * Interface for facilitating notification of received messages in the in-Bound sub-component of a node.
 */
public interface IMessageCallback {

    void onMessageReceived(CRDTLog crdtLog);

    void onPeerConnectionReceived(Peer incomingPeer);

    void onSnapshotReceived(CRDTController snapshot);
}
