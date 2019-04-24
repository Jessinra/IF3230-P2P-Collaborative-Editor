package Frontend.Class;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UsernameBox {

    public static String CREATE = "Create";
    public static String JOIN = "Join";

    public static String submitType = UsernameBox.JOIN;
    public static String username;
    public static String ip_address;
    public static int port;

    static void display(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        /* Initialize components */
        // Username
        Label usernameLabel = new Label("Username");
        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameLabel, 8, 0);
        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        GridPane.setConstraints(usernameTextField, 10, 0);

        // IP
        Label ipLabel = new Label("IP");
        TextField ipTextField = new TextField();
        GridPane.setConstraints(ipLabel, 8, 2);
        GridPane.setHalignment(ipLabel, HPos.RIGHT);
        GridPane.setConstraints(ipTextField, 10, 2);

        // Port
        TextField portTextField = new TextField();
        portTextField.setPrefColumnCount(6);
        GridPane.setConstraints(portTextField, 11, 2);

        // Radio Buttons
        final ToggleGroup radioGroup = new ToggleGroup();
        // createRadio
        RadioButton createRadio = new RadioButton();
        createRadio.setText(UsernameBox.CREATE);
        createRadio.setUserData(UsernameBox.CREATE);
        createRadio.setToggleGroup(radioGroup);
        createRadio.setSelected(false);
        GridPane.setConstraints(createRadio, 10, 3);
        GridPane.setHalignment(createRadio, HPos.LEFT);

        // joinRadio
        RadioButton joinRadio = new RadioButton();
        joinRadio.setText(UsernameBox.JOIN);
        joinRadio.setUserData(UsernameBox.JOIN);
        joinRadio.setToggleGroup(radioGroup);
        joinRadio.setSelected(true);
        GridPane.setConstraints(joinRadio, 10, 3);
        GridPane.setHalignment(joinRadio, HPos.RIGHT);

        //radioGroup
        radioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (radioGroup.getSelectedToggle() != null) {
                submitType = radioGroup.getSelectedToggle().getUserData().toString();
                System.out.println("submitType: " + submitType);

                if (submitType.equals(UsernameBox.CREATE)) {
                    ipTextField.clear();
                    portTextField.clear();
                    ipTextField.setDisable(true);
                    portTextField.setDisable(true);
                } else {
                    ipTextField.clear();
                    portTextField.clear();
                    ipTextField.setDisable(false);
                    portTextField.setDisable(false);
                }
            }
        });

        // Button
        Button submitButton = new Button("GO!");
        submitButton.setOnAction(e -> {
            if (submitType.equals(UsernameBox.CREATE) && !usernameTextField.getText().isEmpty()) {
                username = usernameTextField.getText();
            } else if (submitType.equals(UsernameBox.JOIN) && !usernameTextField.getText().isEmpty() &&
                    !ipTextField.getText().isEmpty() && !portTextField.getText().isEmpty()) {

                username = usernameTextField.getText();
                ip_address = ipTextField.getText();
                port = Integer.parseInt(portTextField.getText());
            }

            window.close();
        });
        GridPane.setConstraints(submitButton, 10, 4);
        GridPane.setHalignment(submitButton, HPos.CENTER);


        // Handle Close Request
        window.setOnCloseRequest(e -> Platform.exit());

        // Grid Pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.getChildren().addAll(usernameLabel, usernameTextField,
                ipLabel, ipTextField, portTextField, createRadio, joinRadio,
                submitButton);

        window.setScene(new Scene(grid, 600, 180));
        window.showAndWait();
    }

    private static Color stringToFXColor(String str_color) {
        switch (str_color) {
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
