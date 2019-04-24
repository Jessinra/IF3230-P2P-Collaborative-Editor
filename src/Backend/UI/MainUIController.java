package Backend.UI;

import Backend.CRDT.CRDTChar;
import Backend.CRDT.CRDTController;
import Backend.CRDT.CRDTLog;
import Backend.P2PServer.Node;
import Backend.P2PServer.Peer;
import Frontend.Class.UsernameBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.Queue;

public class MainUIController implements IEditorCallback {

    private Integer WRAP_LENGTH = 125;
    private Integer cursorPosition = 0;
    private Integer peersCount = 1;

    private String username;
    private int joinTargetPort;

    private Color color;
    private CRDTController crdtController;
    private Queue<CRDTChar> deletion_buffer;

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

                // Receive data from p2p then RemoteInsert/RemoteDelete
                // if it remote delete, insert the operation to deletion buffer

                // Execute remote buffer
                /*
                if(isInsertOperationExist(deletion_buffer.peek())){
                    crdtController.remoteDelete(deletion_buffer.remove());
                }
                */


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

    public void onRemoteUpdateReceived() {
        // TODO : implement this
    }

    public void onPeerJoinRequestReceived() {
        // TODO: implement this
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

    private void addCRDTCharToDeletionBuffer(CRDTChar crdt_char){
        deletion_buffer.add(crdt_char);
    }

    private Boolean isInsertOperationExist(CRDTChar crdt_char){
        Boolean isExist = false;

        for(int i = 0; i < crdtController.getVersionVector().size(); i++){
            CRDTLog log = crdtController.getLogAt(i);

            if(log.getOperation() == 1 && log.getUpdate() == crdt_char){
                isExist = true;
            }
        }

        return isExist;
    }

    @Override
    public void onRemoteUpdate(CRDTLog crdtLog) {
        // TODO
        // check for type
        // do the remoteInsert / remoteDelete
        // insert into version vector
    }

    @Override
    public void onPeerJoined(Peer incomingPeer) {
        // TODO
        // share the peer list
        // share the current log
    }
}
