package Backend.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PeerBoxController {

    @FXML
    private Rectangle peer_rectangle;
    @FXML
    private Label peer_label;


    /* =================================================================
                                    GETTER
    ================================================================= */
    public Rectangle getRectangle() {
        return peer_rectangle;
    }

    public Label getLabel() {
        return peer_label;
    }

    public String getLabelText() {
        return peer_label.getText();
    }

    /* =================================================================
                                    SETTER
    ================================================================= */
    public void setLabelText(String text) {
        peer_label.setText(text);
    }

    public void setRectangleSize(double width, double height) {
        peer_rectangle.setWidth(width);
        peer_rectangle.setHeight(height);
    }

    public void setRectangleColor(Color color) {
        peer_rectangle.setFill(color);
    }

}
