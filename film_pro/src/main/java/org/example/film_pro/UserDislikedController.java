package org.example.film_pro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class UserDislikedController implements Initializable {

    @FXML
    private HBox Hbox_exit;

    @FXML
    private VBox cardLayout;

    @FXML
    private GridPane filmContainer;

    @FXML
    private HBox hbox_disliked;

    @FXML
    private HBox hbox_liked;

    @FXML
    private HBox hbox_menu;

    @FXML
    private Label label_username;


    @FXML
    private HBox hbox_allFilms;


    //TRAVEL BETWEEN PANELS ***************************************************************//
    @FXML
    void toLikedPanel(MouseEvent event){
        Stage currentStage = (Stage) filmContainer.getScene().getWindow();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(UserLikedController.class.getResource("UserLikedPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
            currentStage.setScene(scene);
        }
        catch (IOException var6){
            System.out.println(var6);
        }
    }
    @FXML
    void toMainPanel(MouseEvent event){
        Stage currentStage = (Stage) filmContainer.getScene().getWindow();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(UserLikedController.class.getResource("hello-view.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
            currentStage.setScene(scene);
        }
        catch (IOException var6){
            System.out.println(var6);
        }
    }
    @FXML
    void toDislikedPanel(MouseEvent event){
        Stage currentStage = (Stage) filmContainer.getScene().getWindow();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(UserLikedController.class.getResource("UserDislikedPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
            currentStage.setScene(scene);
        }
        catch (IOException var6){
            System.out.println(var6);
        }
    }
    @FXML
    void toAllFilms(MouseEvent event){
        Stage currentStage = (Stage) filmContainer.getScene().getWindow();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(UserLikedController.class.getResource("AllFilmsPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
            currentStage.setScene(scene);
        }
        catch (IOException var6){
            System.out.println(var6);
        }
    }
    @FXML
    void toExit(MouseEvent event){
        //Closing CurrentScene
        Stage currentStage = (Stage) filmContainer.getScene().getWindow();
        currentStage.close();

        //Opening LoginPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("LoginPanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    private StackList<Disliked> dislikeds;
    private LinkedList<Film> films= new LinkedList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label_username.setText(LoginController.activeUser.getName());
        DbHelper db = new DbHelper();
        int column = 0;
        int row = 1;
        try {
            dislikeds = db.sqlSelectDislikes(LoginController.activeUser.getId());
            Node<Disliked> temp = dislikeds.head;
            while (temp != null){
                int film_id = temp.data.getId_film();
                Film f = db.sqlSelectFilm(film_id);
                films.add(f);
                temp = temp.next;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            Node<Film> temp = films.head;
            while(temp!=null){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));
                VBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(temp.data);
                if(column == 5){
                    column = 0;
                    row++;
                }
                filmContainer.add(cardBox,column++,row);
                GridPane.setMargin(cardBox,new Insets(10));
                temp = temp.next;
            }
        }catch (IOException ex){
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//TRAVEL BETWEEN PANELS ***************************************************************//







}
