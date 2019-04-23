package Frontend.Class;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InputBox {

    Stage window;
    String result;

    public static void display(String message){
        // Initialize Window
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setWidth(200);

        // Label
        Label label = new Label();
        label.setText(message);

        // Textfield
        TextField textField = new TextField();
        textField.setPrefWidth(200);

        // Layout
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10,10,10,10));

        // Add items
        borderPane.setTop(label);
        borderPane.setCenter(textField);

        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.show();
    }

}
