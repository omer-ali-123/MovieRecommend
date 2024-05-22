package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {


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
//TRAVEL BETWEEN PANELS ***************************************************************//
    private List<String> categories = new ArrayList<>(List.of("Comedy","Fantasy","Drama","Horror",
            "Adventure","Thriller","Cartoon","Action","Sci-Fi","Biography"));

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

    private LinkedList<Film>  recommended;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label_username.setText(LoginController.activeUser.getName());
        DbHelper db = new DbHelper();
        int column = 0;
        int row = 1;
        try {
            recommended = showNewList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            Node<Film> temp = recommended.head;
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

    private List<String> categoryLikedOrNot() throws SQLException {
        DbHelper db = new DbHelper();
        List<Category> likes = db.sqlSelectLikedCategories(LoginController.activeUser.getId());
        List<Category> dislikes = db.sqlSelectDislikedCategories(LoginController.activeUser.getId());
        List<String> likedCategories = new ArrayList<String>();
        int flag =0;
        for (Category liked : likes){
            for (Category disliked: dislikes){
                if(liked.getCategoryName().equals(disliked.getCategoryName())){
                    int counter = liked.getCategoryCount()-disliked.getCategoryCount();
                    flag++;
                    if(counter>0){
                        likedCategories.add(liked.getCategoryName());
                        break;
                    }

                }
            }
            if(flag == 0){
                likedCategories.add(liked.getCategoryName());
            }
            flag=0;
        }
        return likedCategories;
    }
    private List<String> categoryDislikedOrNot() throws SQLException {
        DbHelper db = new DbHelper();
        List<Category> likes = db.sqlSelectLikedCategories(LoginController.activeUser.getId());
        List<Category> dislikes = db.sqlSelectDislikedCategories(LoginController.activeUser.getId());
        List<String> dislikedCategories = new ArrayList<String>();
        int flag = 0;
        for (Category disliked : dislikes){
            for (Category liked: likes){
                if(liked.getCategoryName() == disliked.getCategoryName()) {
                    int counter = disliked.getCategoryCount() - liked.getCategoryCount();
                    flag++;
                    if (counter > 0) {
                        dislikedCategories.add(disliked.getCategoryName());
                        break;
                    }
                }
            }
            if(flag == 0){
                dislikedCategories.add(disliked.getCategoryName());
            }
            flag = 0;
        }
        return dislikedCategories;
    }

    private LinkedList<Film> showNewList() throws SQLException {
        DbHelper db = new DbHelper();
        // Getting liked films and categories
        List<String> likedCategories = categoryLikedOrNot();
        LinkedList<Film> likes = db.executeQuery(likedCategories);
        if(likes == null){
            likes = new LinkedList<Film>();
        }
        // Getting disliked films and categories
        List<String> dislikedCategories = categoryDislikedOrNot();
        LinkedList<Film> dislikes = db.executeQuery(dislikedCategories);
        if(dislikes == null){
            dislikes = new LinkedList<Film>();
        }
        // Getting other categories
        List<String> allCategories = categories;
        allCategories.removeAll(likedCategories);
        allCategories.removeAll(dislikedCategories);
        LinkedList<Film> neither = db.executeQuery(allCategories);
        if(neither == null){
            neither = new LinkedList<Film>();
        }

        //create random class
        Random rnd = new Random();

        LinkedList<Film> lastList = new LinkedList<>();
        int counter =0;
        int index = 0;

        while(likes.head != null && counter<12){
            index = rnd.nextInt(likes.elementCount());
            lastList.add(likes.getData(index));
            likes.deleteData(index);
            counter++;
        }
        while(dislikes.head != null && counter<15){
            index = rnd.nextInt(dislikes.elementCount());
            lastList.add(dislikes.getData(index));
            dislikes.deleteData(index);
            counter++;
        }
        while(neither.head != null && counter<20){
            index = rnd.nextInt(neither.elementCount());
            lastList.add(neither.getData(index));
            neither.deleteData(index);
            counter++;
        }
        //if there are not enough datas inside of like or dislikes disloop will trigger
        while(counter<20){
            if(likes.head != null){
                index = rnd.nextInt(likes.elementCount());
                lastList.add(likes.getData(index));
                likes.deleteData(index);
                counter++;
            }
            if(neither.head != null){
                index = rnd.nextInt(neither.elementCount());
                lastList.add(neither.getData(index));
                neither.deleteData(index);
                counter++;
            }
            if(dislikes.head != null){
                index = rnd.nextInt(dislikes.elementCount());
                lastList.add(dislikes.getData(index));
                dislikes.deleteData(index);
                counter++;
            }

        }
        return lastList;
    }
}
