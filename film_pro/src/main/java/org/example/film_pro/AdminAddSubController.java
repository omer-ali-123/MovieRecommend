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

public class AdminAddSubController implements Initializable {

    @FXML
    private HBox Hbox_exit;

    @FXML
    private VBox cardLayout;

    @FXML
    private GridPane filmContainer;

    @FXML
    private HBox hbox_add;
    @FXML
    private Button btn_refresh;

    @FXML
    private TextField text_search;
    private LinkedList recommended;


    //******************************TRAVELLING BETWEEN PANELS ***************************************//
    @FXML
    void toAddPanel(MouseEvent event){
        //Opening FirstPanel Scene
        Stage stage = new Stage();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(AdminAddMovieController.class.getResource("AdminAddMoviePanel.fxml"));

        try{
            scene = new Scene((Parent) fxmlLoader.load());
        }
        catch (IOException var6){
            System.out.println(var6);
        }
        stage.setTitle("Add Page");
        stage.setScene(scene);
        stage.show();
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
    //******************************TRAVELLING BETWEEN PANELS ***************************************//


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    refresh();
    }

    @FXML
    public void buttonRefresh(ActionEvent event){
        refresh();
    }

    public void refresh(){
        filmContainer.getChildren().clear();
        DbHelper db = new DbHelper();
        int column = 0;
        int row = 1;
        try {
            recommended = db.sqlSelectFilms();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            Node<Film> temp = recommended.head;

            while(temp != null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("EmptyCard.fxml"));
                VBox cardBox = fxmlLoader.load();
                EmptyCardController cardController = fxmlLoader.getController();
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
            if(searchedFilm !=null){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("EmptyCard.fxml"));
                VBox cardBox = fxmlLoader.load();
                EmptyCardController cardController = fxmlLoader.getController();
                cardController.setData(searchedFilm);
                filmContainer.add(cardBox, column, row);
                GridPane.setMargin(cardBox, new Insets(10));            }
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

}
