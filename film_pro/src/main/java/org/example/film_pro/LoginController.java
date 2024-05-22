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
import java.sql.SQLException;

public class LoginController {

    @FXML
    private GridPane SceneLoginPanelGridPane;

    @FXML
    private Button btn_Lregister;

    @FXML
    private Button btn_back;

    @FXML
    private Label lbl_check;

    @FXML
    private Hyperlink link_toregisterpanel;

    @FXML
    private PasswordField txt_Lpassword;

    @FXML
    private TextField txt_Lusername;

    @FXML
    void backButton(ActionEvent event){
        backToMainPage();
    }
    @FXML
    void linkClick(ActionEvent event){
        toRegisterPage();
    }

    public  static User activeUser = null;

    @FXML
    void loginButton(ActionEvent event) throws SQLException {
        //checking is areas empty
        if (txt_Lusername.getText().isEmpty() && txt_Lpassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Be careful!");
            alert.setContentText("Username and password areas should be filled.");
            alert.showAndWait();
        }
        else {
            //checking is this user in database
            DbHelper db = new DbHelper();
            User loginUser = db.sqlSelectUser(txt_Lusername.getText(), txt_Lpassword.getText());
            if (loginUser == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Ooops!");
                alert.setContentText("User couldn't be found.");

                alert.showAndWait();
            }
            else{
                // login
                activeUser = loginUser;
                toMainStage();
            }
        }
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
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("Main Page");
        stage.setScene(scene);
        stage.show();
    }

    void toRegisterPage(){
        //Closing CurrentScene
        Stage currentStage = (Stage) link_toregisterpanel.getScene().getWindow();
        currentStage.close();

        //Opening RegisterPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterController.class.getResource("RegisterPanel.fxml"));

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
    void toMainStage(){
        //Closing CurrentScene
        Stage currentStage = (Stage) btn_Lregister.getScene().getWindow();
        currentStage.close();

        //Opening MainPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("hello-view.fxml"));

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
