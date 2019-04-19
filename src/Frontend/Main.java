package Frontend;

import Backend.UIControllers.PeersController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Stack;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        // Initialize Main UI FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/MainUI.fxml"));

        Parent root = (Parent) loader.load();
        primaryStage.setTitle("Peer2Peer Collaborative Editing");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

    }

}
