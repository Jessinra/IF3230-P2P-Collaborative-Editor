package Backend.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PeersController {

    @FXML private VBox VBox_peer;
    @FXML private Label label1;
    @FXML private Button button1;

    // PeerBoxControllers
    @FXML private PeerBoxController peer1Controller;
    @FXML private PeerBoxController peer2Controller;
    @FXML private PeerBoxController peer3Controller;
    @FXML private PeerBoxController peer4Controller;
    @FXML private PeerBoxController peer5Controller;

    double computeTextWidth(Font font, String text, double wrappingWidth) {
        Text helper = new Text();
        helper.setFont(font);
        helper.setText(text);
        // Note that the wrapping width needs to be set to zero before
        // getting the text's real preferred width.
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        double w = Math.min(helper.prefWidth(-1), wrappingWidth);
        helper.setWrappingWidth((int)Math.ceil(w));
        double textWidth = Math.ceil(helper.getLayoutBounds().getWidth());
        return textWidth;
    }

    public void changePeer(int num, String text_label, Color color){
        Text temp = new Text(text_label);
        double text_label_width;
        switch (num){
            case 1:
                peer1Controller.setRectangleColor(color);
                peer1Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peer1Controller.setRectangleSize(text_label_width,20);
                break;
            case 2:
                peer2Controller.setRectangleColor(color);
                peer2Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peer2Controller.setRectangleSize(text_label_width,20);
                break;
            case 3:
                peer3Controller.setRectangleColor(color);
                peer3Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peer3Controller.setRectangleSize(text_label_width,20);
                break;
            case 4:
                peer4Controller.setRectangleColor(color);
                peer4Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peer4Controller.setRectangleSize(text_label_width,20);
                break;
            case 5:
                peer5Controller.setRectangleColor(color);
                peer5Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peer5Controller.setRectangleSize(text_label_width,20);
                break;
            default:
                break;
        }
    }
}
