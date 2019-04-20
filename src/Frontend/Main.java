package Frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
