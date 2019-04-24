package Backend.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PeersController {

    @FXML
    private VBox VBox_peer;
    @FXML
    private Label label1;
    @FXML
    private Button button1;

    // Labels
    @FXML
    private Label peer1;
    @FXML
    private Label peer2;
    @FXML
    private Label peer3;
    @FXML
    private Label peer4;
    @FXML
    private Label peer5;


    double computeTextWidth(Font font, String text, double wrappingWidth) {

        Text helper = new Text();

        helper.setFont(font);
        helper.setText(text);

        // Note that the wrapping width needs to be set to zero before
        // getting the text's real preferred width.

        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);

        double w = Math.min(helper.prefWidth(-1), wrappingWidth);
        helper.setWrappingWidth((int) Math.ceil(w));
        return Math.ceil(helper.getLayoutBounds().getWidth());
    }

    void changePeer(int num, String text_label) {

        switch (num) {
            case 1:
                peer1.setText(text_label);
                break;
            case 2:
                peer2.setText(text_label);
                break;
            case 3:
                peer3.setText(text_label);
                break;
            case 4:
                peer4.setText(text_label);
                break;
            case 5:
                peer5.setText(text_label);
                break;
            default:
                break;
        }
    }
}
