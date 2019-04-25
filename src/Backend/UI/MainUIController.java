package Backend.UI;

import Backend.CRDT.CRDTChar;
import Backend.CRDT.CRDTController;
import Backend.CRDT.CRDTLog;
import Backend.P2PServer.Message;
import Backend.P2PServer.Node;
import Backend.P2PServer.Peer;
import Frontend.Class.UsernameBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MainUIController implements IEditorCallback {

    private Integer cursorPosition = 0;
    private String username;
    private CRDTController crdtController;
    private Node nodeClient;

    @FXML
    private PeersController peersController;
    @FXML
    private TextArea main_text_area;


    public MainUIController() {

        // Show UsernameBox
        username = UsernameBox.username;

        // Initialize Classes & Lists
        crdtController = new CRDTController(username);

        initializeNodeClient();

        Thread node1Thread = new Thread(() -> this.nodeClient.start());
        Thread node2Thread = new Thread(() -> {
            if (UsernameBox.submitType.equals(UsernameBox.JOIN)) {
                System.out.println("Joining peer...");
                nodeClient.sendJoinRequest(UsernameBox.ip_address, UsernameBox.port);
            }
        });

        node1Thread.start();
        node2Thread.start();
    }

    private void initializeNodeClient() {
        this.nodeClient = new Node(this.username, this);
    }

    public void handleTextAreaInput(KeyEvent ev) {

        // Initialize Peers
        peersController.changePeer(1, crdtController.getClientId());
        cursorPosition = main_text_area.getCaretPosition();

        if (ev.getCode().isMediaKey() ||
                ev.getCode().isFunctionKey() ||
                ev.getCode().isModifierKey() ||
                ev.getCode().isNavigationKey() ||
                ev.getCode().isArrowKey()) {

        }

        // Handle BackSpace
        else if (ev.getCode() == KeyCode.BACK_SPACE) {
            if (cursorPosition > 0) {
                CRDTChar updatedChar = crdtController.localDelete(cursorPosition - 1);
                nodeClient.broadcastLocalDelete(updatedChar);
            }
        }

        // Handle Delete Button
        else if (ev.getCode() == KeyCode.DELETE) {
            if (cursorPosition < crdtController.getTextContent().size()) {
                CRDTChar updatedChar = crdtController.localDelete(cursorPosition);
                nodeClient.broadcastLocalDelete(updatedChar);
            }
        }

        // 'Enter' is kinda different from other whitespace, so to print out , need \n instead of normal enter key
        else if (ev.getCode() == KeyCode.ENTER) {
            CRDTChar updatedChar = crdtController.localInsert('\n', cursorPosition);
            nodeClient.broadcastLocalInsert(updatedChar);
        }

        // Insert letter
        else {
            CRDTChar updatedChar = crdtController.localInsert((ev.getText()).charAt(0), cursorPosition);
            nodeClient.broadcastLocalInsert(updatedChar);
        }

        refreshUI();
    }


    public void resetCursor() {
        cursorPosition = main_text_area.getCaretPosition();
        refreshUI();
    }

    private void refreshUI() {

        try {
            int prevPosition = main_text_area.getCaretPosition();
            main_text_area.setText(crdtController.getText());
            main_text_area.positionCaret(prevPosition);

        } catch (Exception e) {

            main_text_area.setText(crdtController.getText());
            main_text_area.positionCaret(0);
        }

        System.out.println(crdtController.getText());
    }

    @Override
    public void onRemoteUpdate(CRDTLog crdtLog) {

        if (crdtLog.getOperation() == CRDTLog.INSERT) {
            crdtController.remoteInsert(crdtLog.getUpdate());

        } else if (crdtLog.getOperation() == CRDTLog.DELETE) {
            crdtController.addCRDTLogToDeletionBuffer(crdtLog);
        }

        refreshUI();

        if (crdtController.getDeletionBuffer().isEmpty()) {
            return;
        }

        if (crdtController.isInsertOperationExist(crdtController.getDeletionBuffer().get(0))) {
            crdtController.remoteDelete(crdtController.getDeletionBuffer().remove(0).getUpdate());
        }

        refreshUI();
    }

    @Override
    public void onPeerJoined(Peer incomingPeer) {

        // Sending own data to them
        Message ownPeerInfo = new Message(this.username, nodeClient.getSelfPeer());
        nodeClient.sendMessage(ownPeerInfo, incomingPeer);

        // Sending all listed peer to incoming peer
        for (Peer peer : nodeClient.getPeerList()) {
            Message peerInfo = new Message(this.username, peer);
            nodeClient.sendMessage(peerInfo, incomingPeer);
        }

        Message newPeerInfo = new Message(this.username, incomingPeer);
        nodeClient.broadcastMessage(newPeerInfo);

        nodeClient.addUniquePeer(incomingPeer);

        // TODO : verify this
        Message snapshotMsg = new Message(this.username, this.crdtController);
        nodeClient.sendMessage(snapshotMsg, incomingPeer);

        System.out.println(nodeClient.getPeerList());
    }

    @Override
    public void onSyncSnapshot(CRDTController snapshot) {

        if (this.crdtController.getVersionVector().size() < snapshot.getVersionVector().size()) {
            this.crdtController = snapshot;
            refreshUI();
        }
    }
}
