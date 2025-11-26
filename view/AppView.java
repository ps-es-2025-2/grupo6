package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;

public class AppView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            URL url = new File("view/app.fxml").toURI().toURL();
            Pane pane = FXMLLoader.load(url);
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EasyStop - Gestão de Estacionamento");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}