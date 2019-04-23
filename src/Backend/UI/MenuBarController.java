package Backend.UI;

import Frontend.Class.InputBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuBarController {

    // File's Menu Item
    @FXML private MenuItem file_new;
    @FXML private MenuItem file_open;
    @FXML private MenuItem file_exit;


    public void createNewFile(){
        InputBox.display("Enter new file's name: ");
    }

    public void exitWindow(){
        Platform.exit();
    }
}
