package Backend.UI;

import Backend.CRDT.CRDTController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainUIController {

    private Integer cursorPosition;
    private CRDTController crdtController;
    @FXML private TextArea main_text_area;


    public MainUIController(){
        cursorPosition = 0;
        crdtController = new CRDTController("1");
    }

    public void handleTextAreaInput(KeyEvent ev){
        // System.out.println("Keypress code: " + ev.getCode());

        if(ev.getCode() == KeyCode.LEFT){
            decreaseCursorPosition(1);
            System.out.println("Left, cursorPosition: " + cursorPosition.toString());
        }
        else if(ev.getCode() == KeyCode.RIGHT){
            increaseCursorPosition(1);
            System.out.println("Right, cursorPosition: " + cursorPosition.toString());
        }
        else if(ev.getCode() == KeyCode.UP){
            System.out.println("UP, cursorPosition: " + cursorPosition.toString());
        }
        else if(ev.getCode() == KeyCode.DOWN){
            System.out.println("DOWN, cursorPosition: " + cursorPosition.toString());
        }
        else if(ev.getCode() == KeyCode.BACK_SPACE){
            crdtController.localDelete(cursorPosition-1);
            System.out.println(crdtController.getText());
            decreaseCursorPosition(1);
        }
        else {
            crdtController.localInsert((ev.getText()).charAt(0), cursorPosition);
            increaseCursorPosition(1);
            System.out.println(crdtController.getText() + ", cursorPosition: " + cursorPosition.toString());
        }
    }

    private void decreaseCursorPosition(int value){
        if(cursorPosition <= 0){
            cursorPosition = 0;
        }
        else {
            cursorPosition -= value;
        }
    }

    private void increaseCursorPosition(int value){
        if(cursorPosition < crdtController.getTextContent().size()){
            cursorPosition += value;
        }
        else {
            cursorPosition = crdtController.getTextContent().size();
        }

    }

}
