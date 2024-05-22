package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.util.List;
import java.util.ResourceBundle;

public class AllFilmsController implements Initializable {

    @FXML
    private HBox Hbox_exit;

    @FXML
    private VBox cardLayout;

    @FXML
    private GridPane filmContainer;

    @FXML
    private HBox hbox_allFilms;

    @FXML
    private HBox hbox_disliked;

    @FXML
    private HBox hbox_liked;

    @FXML
    private HBox hbox_menu;

    @FXML
    private Button btn_ara;

    @FXML
    private Label label_username;

    @FXML
    private TextField text_search;


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

    private LinkedList allFilms;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label_username.setText(LoginController.activeUser.getName());
        DbHelper db = new DbHelper();
        int column = 0;
        int row = 1;
        try {
            allFilms = db.sqlSelectFilms();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            Node<Film> temp = allFilms.head;
            while(temp != null){
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
    @FXML
    public void searchFilm(ActionEvent event) throws SQLException {
        int column = 0;
        int row = 1;
        try {
            DbHelper db = new DbHelper();
            filmContainer.getChildren().clear();
            String searchedText = text_search.getText().substring(0, 1).toUpperCase() + text_search.getText().substring(1);
            Film searchedFilm = db.sqlSelectFilmName(searchedText);
            if(searchedFilm!=null){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));
                VBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(searchedFilm);
                filmContainer.add(cardBox, column, row);
                GridPane.setMargin(cardBox, new Insets(10));
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Ooops!");
                alert.setContentText("We didn't find this film. Please check film name.");
                alert.showAndWait();
            }


        }catch (IOException ex){
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//TRAVEL BETWEEN PANELS ***************************************************************//
}
