package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterController {

    @FXML
    private Button btn_register;

    @FXML
    private Label lbl_;

    @FXML
    private Hyperlink link_1;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField txt_name;

    @FXML
    private PasswordField txt_password;

    @FXML
    private PasswordField txt_repeatpassword;

    @FXML
    private TextField txt_surname;

    @FXML
    private TextField txt_username;

    @FXML
    void buttonRegister(ActionEvent event) throws SQLException{
        if (txt_name.getText().isEmpty() && txt_surname.getText().isEmpty() && txt_username.getText().isEmpty() && txt_password.getText().isEmpty() && txt_repeatpassword.getText().isEmpty()){
        //Checking is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Be careful!");
            alert.setContentText("Register areas should be filled.");
            alert.showAndWait();
        }
        else {
            DbHelper db = new DbHelper();
            User user = db.sqlCheckUsername(txt_username.getText());
            if (user != null) {
            //checking is this username got before
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Ooops!");
                alert.setContentText("This username is tooken before");
                alert.showAndWait();
            } else if (!txt_password.getText().equals(txt_repeatpassword.getText())) {
                //Checking passwords are same or not
                System.out.println(txt_password.getText());
                System.out.println(txt_repeatpassword.getText());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Ooops!");
                alert.setContentText("Passwords must match");
                alert.showAndWait();
            } else {
                //inserting new user to SQL
                User registeredUser = new User(0,txt_name.getText(),txt_surname.getText(),txt_username.getText(),txt_password.getText());
                int kontrol = db.sqlInsertUser(registeredUser);
                if(kontrol>0) {
                    //to login panel
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Congrats!");
                    alert.setContentText("You registered, you can login");
                    alert.showAndWait();
                    toMainStage();
                }
            }
        }
    }
    @FXML
    void linkRegister(ActionEvent event) {
            toMainStage();

    }


    void toMainStage(){
        //Closing CurrentScene
        Stage currentStage = (Stage) btn_register.getScene().getWindow();
        currentStage.close();

        //Opening FirstPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("LoginPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("Login Panel");
        stage.setScene(scene);
        stage.show();
    }




}
