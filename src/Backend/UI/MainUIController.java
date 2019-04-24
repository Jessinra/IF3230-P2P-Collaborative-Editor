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
    private int joinTargetPort;

    private CRDTController crdtController;

    @FXML
    private PeersController peersController;
    @FXML
    private TextArea main_text_area;

    private Node nodeClient;

    public MainUIController() {

        // Show UsernameBox
        UsernameBox.display("Peer2Peer Collaborative Editing");
        username = UsernameBox.username;
        joinTargetPort = UsernameBox.port;

        // Initialize Classes & Lists
        crdtController = new CRDTController(username);
        initializeNodeClient();
    }

    public void initializeNodeClient() {
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
            return;
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

        System.out.println("========");
        System.out.println(crdtController.getText());
    }

    public void resetCursor() {
        cursorPosition = main_text_area.getCaretPosition();
        System.out.println("Clicked Back, cursorPosition: " + cursorPosition.toString());
    }

    @Override
    public void onRemoteUpdate(CRDTLog crdtLog) {
        if (crdtLog.getOperation() == CRDTLog.INSERT) {
            crdtController.remoteInsert(crdtLog.getUpdate());
        } else if (crdtLog.getOperation() == CRDTLog.DELETE) {
            crdtController.addCRDTLogToDeletionBuffer(crdtLog);
        }

        if (!crdtController.getDeletionBuffer().isEmpty() &&
                crdtController.isInsertOperationExist(crdtController.getDeletionBuffer().get(0))) {
            crdtController.remoteDelete(crdtController.getDeletionBuffer().remove(0).getUpdate());
        }
    }

    @Override
    public void onPeerJoined(Peer incomingPeer) {
        // Sending all listed peer to incoming peer

        for (Peer peer : nodeClient.getPeerList()) {
            Message peerInfo = new Message(this.username, peer);
            nodeClient.sendMessage(peerInfo, incomingPeer);
        }

        Message newPeerInfo = new Message(this.username, incomingPeer);
        nodeClient.broadcastMessage(newPeerInfo);
    }
}
