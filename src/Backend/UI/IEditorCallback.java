package Backend.UI;

import Backend.CRDT.CRDTController;
import Backend.CRDT.CRDTLog;
import Backend.P2PServer.Peer;

public interface IEditorCallback {

    void onRemoteUpdate(CRDTLog crdtLog);

    void onPeerJoined(Peer incomingPeer);

    void onSyncSnapshot(CRDTController snapshot);
}
