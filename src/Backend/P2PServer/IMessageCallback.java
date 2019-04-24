package Backend.P2PServer;

import Backend.CRDT.CRDTLog;

/**
 * Interface for facilitating notification of received messages in the in-Bound sub-component of a node.
 */
public interface IMessageCallback {
    abstract void onMessageReceived(CRDTLog crdtLog);
    abstract void onPeerConnectionReceived(Peer incomingPeer);
}
