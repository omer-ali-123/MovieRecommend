package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstController {

    @FXML
    private GridPane SceneFirstPanelGridPane;

    @FXML
    private Button btn_admin;

    @FXML
    private Button btn_user;

    void SceneUsertoLoginPanel() {

        //Closing CurrentScene
        Stage currentStage = (Stage) btn_user.getScene().getWindow();
        currentStage.close();

        //Opening UserPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("LoginPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void userButton(ActionEvent event){
        SceneUsertoLoginPanel();
    }
    @FXML
    void adminButton(ActionEvent event){
        SceneUsertoAdminLoginPanel();
    }


    void SceneUsertoAdminLoginPanel() {
        //Closing CurrentScene
        Stage currentStage = (Stage) btn_user.getScene().getWindow();
        currentStage.close();

        //Opening AdminLoginPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(AdminLoginController.class.getResource("AdminLoginPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.show();
    }
}
