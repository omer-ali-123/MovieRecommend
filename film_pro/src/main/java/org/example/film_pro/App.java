package org.example.film_pro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("FirstPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FirstPanel");
        stage.setScene(scene);
        stage.show();
        
    }
    public static void main(String[] args) {
        launch();
    }
}
