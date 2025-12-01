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
            
            // Carrega o stylesheet principal da aplicação
            // CSS faz parte da camada View (MVC) - responsável pela apresentação visual
            URL cssUrl = new File("view/styles/main.css").toURI().toURL();
            scene.getStylesheets().add(cssUrl.toExternalForm());
            
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