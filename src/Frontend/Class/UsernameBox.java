package Frontend.Class;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UsernameBox {

    public static String username;
    public static Color color;

    public static void display(String title){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        /* Initialize components */
        // Username
        Label usernameLabel = new Label("Username: ");
        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameLabel, 8, 0);
        GridPane.setConstraints(usernameTextField, 9, 0);

        // Color
        Label colorLabel = new Label("Color: ");
        ChoiceBox<String> colorChoices = new ChoiceBox<>();
        colorChoices.getItems().addAll("AQUA", "LIGHTBLUE", "ORANGE", "LIMEGREEN");
        colorChoices.setValue("AQUA");
        GridPane.setConstraints(colorLabel, 8, 1);
        GridPane.setConstraints(colorChoices, 9, 1);

        // Button
        Button button = new Button("Join");
        button.setOnAction(e -> {
            if(!usernameTextField.getText().isEmpty()){
                username = usernameTextField.getText();
                color = stringToFXColor(colorChoices.getValue());
                window.close();
            }
        });
        GridPane.setConstraints(button, 9, 3);

        // Handle Close Request
        window.setOnCloseRequest(e -> Platform.exit());

        // Grid Pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.getChildren().addAll(usernameLabel, usernameTextField, colorLabel, colorChoices, button);

        window.setScene(new Scene(grid, 450, 150));
        window.showAndWait();
    }

    private static Color stringToFXColor(String str_color){
        switch (str_color){
            case "AQUA":
                return Color.AQUA;
            case "LIGHTBLUE":
                return Color.LIGHTBLUE;
            case "ORANGE":
                return Color.ORANGE;
            case "LIMEGREEN":
                return Color.LIMEGREEN;
            default:
                return Color.AQUA;
        }
    }
}
