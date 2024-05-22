package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchController {

    @FXML
    private Button btn_back;

    @FXML
    private TextField txt_search;

    @FXML
    void buttonBack(ActionEvent event){
        toRecommendPage();
    }


    void toRecommendPage(){
        //Closing CurrentScene
        Stage currentStage = (Stage) btn_back.getScene().getWindow();
        currentStage.close();

        //Opening SearchPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(org.example.film_pro.MainController.class.getResource("hello-view.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("Register Page");
        stage.setScene(scene);
        stage.show();
    }





}
