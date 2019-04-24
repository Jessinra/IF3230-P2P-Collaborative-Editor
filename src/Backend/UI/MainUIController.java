package Backend.UI;

import Backend.CRDT.CRDTController;
import Backend.CRDT.CRDTChar;
import Backend.CRDT.CRDTLog;
import Frontend.Class.UsernameBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.Queue;

public class MainUIController {

    private Integer WRAP_LENGTH = 125;
    private Integer cursorPosition;
    private Integer peersCount;
    private String username;
    private Color color;
    private CRDTController crdtController;
    private Queue<CRDTChar> deletion_buffer;
    @FXML private PeersController peersController;
    @FXML private TextArea main_text_area;

    public MainUIController() {
        // Initialize Variables
        cursorPosition = 0;
        peersCount = 1;
        username = "";
        color = null;

        // Show UsernameBox
        UsernameBox.display("Peer2Peer Collaborative Editing");
        username = UsernameBox.username;
        color = Color.AQUA;

        // Initialize Classes
        crdtController = new CRDTController(username);


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
}
