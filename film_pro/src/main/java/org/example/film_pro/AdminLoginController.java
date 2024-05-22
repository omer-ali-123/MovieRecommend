package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private GridPane SceneLoginPanelGridPane;

    @FXML
    private Button btn_Lregister;

    @FXML
    private Label lbl_check;

    @FXML
    private PasswordField txt_Lpassword;

    @FXML
    private TextField txt_Lusername;

    @FXML
    private Button btn_back;

    @FXML
    void buttonLogin(ActionEvent event){
        if(txt_Lusername.getText().isEmpty() && txt_Lpassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Be careful!");
            alert.setContentText("Username and password areas should be filled.");
            alert.showAndWait();
        }
        else if(txt_Lusername.getText().equals("1") && txt_Lpassword.getText().equals("1")){
            toAdminAddSubPanel();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Wrong user.");
            alert.showAndWait();
        }


    }
    @FXML
    void buttonBack(ActionEvent event){
        backToMainPage();
    }




    void backToMainPage(){
        //Closing CurrentScene
        Stage currentStage = (Stage) btn_back.getScene().getWindow();
        currentStage.close();

        //Opening FirstPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(FirstController.class.getResource("FirstPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException ex){
            System.out.println(ex);
        }
        stage.setTitle("Main Page");
        stage.setScene(scene);
        stage.show();
    }

    void toAdminAddSubPanel(){
        //Closing CurrentScene
        Stage currentStage = (Stage) btn_Lregister.getScene().getWindow();
        currentStage.close();

        //Opening LikedPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(AdminAddSubController.class.getResource("AdminAddSubPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException ex){
            System.out.println(ex);
        }
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }

}
