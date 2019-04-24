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
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MainUIController implements IEditorCallback {

    private Integer cursorPosition = 0;

    private String username;
    private int joinTargetPort;

    private Color color;
    private CRDTController crdtController;
    private Queue<CRDTLog> deletion_buffer;

    @FXML private PeersController peersController;
    @FXML private TextArea main_text_area;

    private Node nodeClient;

    public MainUIController() {

        // Show UsernameBox
        UsernameBox.display("Peer2Peer Collaborative Editing");
        username = UsernameBox.username;
        joinTargetPort = UsernameBox.port;

        // TODO : random color
        color = Color.AQUA;

        // Initialize Classes
        crdtController = new CRDTController(username);
        initializeNodeClient();

        // Create a thread for remote operations
        Thread object = new Thread(() -> {
            try {
                System.out.println("Remote Thread running");

                // Execute remote buffer
                if(isInsertOperationExist(deletion_buffer.peek())){
                    crdtController.remoteDelete(deletion_buffer.remove().getUpdate());
                }

            } catch (Exception e) {
                System.out.println("Error on Remote Thread: " + e.getMessage());
                System.out.println("Stack Trace: ");
                e.getMessage();
            }
        });

        object.start();
    }

    public void initializeNodeClient() {
        // TODO initialize
        this.nodeClient = new Node(this.username, this);
    }

    public void handleTextAreaInput(KeyEvent ev) {

        // Initialize Peers
        peersController.changePeer(1, crdtController.getClientId(), color);
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
                crdtController.localDelete(cursorPosition - 1);
            }
        }

        // Handle Delete Button
        else if (ev.getCode() == KeyCode.DELETE) {
            if (cursorPosition < crdtController.getTextContent().size()) {
                crdtController.localDelete(cursorPosition);
            }
        }

        // 'Enter' is kinda different from other whitespace, so to print out , need \n instead of normal enter key
        else if (ev.getCode() == KeyCode.ENTER) {
            crdtController.localInsert('\n', cursorPosition);
        }

        // Insert letter
        else {
            crdtController.localInsert((ev.getText()).charAt(0), cursorPosition);
        }

        System.out.println("========");
        System.out.println(crdtController.getText());
    }

    public void resetCursor() {
        cursorPosition = main_text_area.getCaretPosition();
        System.out.println("Clicked Back, cursorPosition: " + cursorPosition.toString());
    }

    private void addCRDTLogToDeletionBuffer(CRDTLog crdtLog) {
        deletion_buffer.add(crdtLog);
    }

    private Boolean isInsertOperationExist(CRDTLog crdtLog) {

        ArrayList<Integer> pos = crdtLog.getUpdate().getPosition();
        char value = crdtLog.getUpdate().getValue();
        String writer_id = crdtLog.getUpdate().getWriterId();
        long timestamp = crdtLog.getUpdate().getTimeStamp();

        Boolean isExist = false;
        for(int i = 0; i < crdtController.getVersionVector().size(); i++){
            CRDTLog log = crdtController.getLogAt(i);
            if (log.getOperation() == CRDTLog.INSERT && log.getUpdate().getValue() == value &&
                log.getUpdate().getPosition() == pos && log.getUpdate().getWriterId().equals(writer_id) &&
                log.getUpdate().getTimeStamp() <= timestamp) {

                isExist = true;
                break;
            }
        }

        return isExist;
    }

    @Override
    public void onRemoteUpdate(CRDTLog crdtLog) {
        if (crdtLog.getOperation() == CRDTLog.INSERT) {
            crdtController.remoteInsert(crdtLog.getUpdate());
        } else if (crdtLog.getOperation() == CRDTLog.DELETE) {
            addCRDTLogToDeletionBuffer(crdtLog);
        }
    }

    @Override
    public void onPeerJoined(Peer incomingPeer) {
        // Sending all listed peer to incoming peer

        for (Peer peer : nodeClient.getPeerList()) {
            Message peerInfo = new Message(this.username, peer);
            nodeClient.sendMessage(peerInfo, incomingPeer);
        }
    }
}
