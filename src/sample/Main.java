package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Martin on 7.2.2017
 * Repository branch: Git/SphereRep
 * Branch initiated: 28.2.2017, {master} branch
 * Content: Main.java, SystemMethods.java, Controller.java, sample.fxml
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spheres");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
    }

}
