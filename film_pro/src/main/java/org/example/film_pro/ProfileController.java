package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    private Hyperlink link_disliked;

    @FXML
    private Hyperlink link_liked;

    @FXML
    void linkLiked(ActionEvent event){
        toLikedPanel();
    }
    @FXML
    void linkDisliked(ActionEvent event){
        toDislikedPanel();
    }



    void toLikedPanel(){
            //Closing CurrentScene
            Stage currentStage = (Stage) link_liked.getScene().getWindow();
            currentStage.close();

            //Opening LikedPanel Scene
            Stage stage = new Stage();
            Scene scene = null;
            FXMLLoader fxmlLoader = new FXMLLoader(UserLikedController.class.getResource("UserLikedPanel.fxml"));

            try{
                scene = new Scene((Parent) fxmlLoader.load());
            }
            catch (IOException var6){
                System.out.println(var6);
            }
            stage.setTitle("Movies");
            stage.setScene(scene);
            stage.show();
        }
    void toDislikedPanel(){
        //Closing CurrentScene
        Stage currentStage = (Stage) link_disliked.getScene().getWindow();
        currentStage.close();

        //Opening LikedPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(UserDislikedController.class.getResource("UserDislikedPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }



}
