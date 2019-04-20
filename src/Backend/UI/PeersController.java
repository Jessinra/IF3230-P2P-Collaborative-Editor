package Backend.UI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PeersController {

    @FXML private VBox VBox_peer;
    @FXML private Label label1;
    @FXML private Button button1;

    // PeerBoxControllers
    @FXML private PeerBoxController peers1Controller;
    @FXML private PeerBoxController peers2Controller;
    @FXML private PeerBoxController peers3Controller;
    @FXML private PeerBoxController peers4Controller;
    @FXML private PeerBoxController peers5Controller;

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
                peers1Controller.setRectangleColor(color);
                peers1Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peers1Controller.setRectangleSize(text_label_width,20);
                break;
            case 2:
                peers2Controller.setRectangleColor(color);
                peers2Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peers2Controller.setRectangleSize(text_label_width,20);
                break;
            case 3:
                peers3Controller.setRectangleColor(color);
                peers3Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peers3Controller.setRectangleSize(text_label_width,20);
                break;
            case 4:
                peers4Controller.setRectangleColor(color);
                peers4Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peers4Controller.setRectangleSize(text_label_width,20);
                break;
            case 5:
                peers5Controller.setRectangleColor(color);
                peers5Controller.setLabelText(text_label);
                text_label_width = computeTextWidth(temp.getFont(), text_label, temp.getWrappingWidth());
                peers5Controller.setRectangleSize(text_label_width,20);
                break;
            default:
                break;
        }
    }
}
