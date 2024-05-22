package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminAddMovieController implements Initializable {

    @FXML
    private Button btn_image;

    @FXML
    private ChoiceBox<String> choicebox_categories;

    @FXML
    private ImageView img_image;

    @FXML
    private TextField txt_moviename;

    //SQL thingies
    Connection connection = null;
    PreparedStatement statement = null;
    DbHelper db = new DbHelper();

    private String imageName;
    private Image image;
    private String movieName;
    private String category;
    private File file;


    //String array for categories
    private String[] categories = {"Comedy","Fantasy","Drama","Horror",
            "Adventure","Thriller","Cartoon","Action","Sci-Fi","Biography"};

    //Function that will try to take image from computer
    //if it's not in the SQL schema it will import it to schema
    //if it is in the SQL it won't insert and will show error

    //That will insert all categories inside choicebox
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choicebox_categories.getItems().setAll(categories);
    }


    @FXML
    public void addImage(ActionEvent event) throws FileNotFoundException {

        //This will take image from user and will convert to File variable.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg","*.jpeg" ,"*.gif")); // limit fileChooser options to image files
        File selectedFile = fileChooser.showOpenDialog(null);
        FileInputStream in = new FileInputStream(selectedFile);

        setFile(selectedFile);

        //That will convert file to image
        this.image = new Image(in);
        setImage(image);
        this.imageName = selectedFile.getName();
        setImageName(imageName);
        //Setting imageView
        img_image.setImage(image);
    }

    //This function will try to add movie to sql and create a movie object.
    //If movie's name is in the sql schema it will print an error and convince the user to try again.

    @FXML
    public void addMovie(ActionEvent event){
        //Connection Section
        try{
            connection = db.getConnection();
            String sql = "select * from film_pro.films where name = ?";
            statement = connection.prepareStatement(sql);

            Image currentImage = img_image.getImage();
            String currentName = txt_moviename.getText();
            String currentCategory = choicebox_categories.getValue();


            //First letter High, Rest are lower.
            currentName= currentName.substring(0, 1).toUpperCase() + currentName.substring(1);
            currentCategory = currentCategory.substring(0, 1).toUpperCase() + currentCategory.substring(1);

            //If the image isn't imported it will show an error.
            if(currentImage == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("You can not add movie without an image");
                alert.setContentText("Please enter an image");
                alert.showAndWait();
            }

            //Checking whatever if there's a movie named like user did name before
            statement.setString(1,currentName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("There is already a movie named like that");
                alert.showAndWait();
            }
            //Now it is time to insert the movie.
            else{
                //I won't create another connection.
                sql = "insert into film_pro.films (category,name,photo,photoName) " +
                        "values (?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1,currentCategory);
                statement.setString(2,currentName);
                statement.setBlob(3,new FileInputStream(getFile()),getFile().length());
                statement.setString(4,getFile().getName());

                int rowsInserted = statement.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText("YOU HAVE INSERTED MOVIE SUCCESSFULLY");
                alert.showAndWait();




            }

        }catch (SQLException exception){
            System.out.println("Code: "+exception.getErrorCode());
            System.out.print(exception.getMessage());
        }
        catch (Exception exception1){
            System.out.println("Code: "+exception1.getMessage());
        }


        //Control Section

    }

    private void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private void setFile(File file) {
        this.file = file;
    }

    private File getFile() {
        return file;
    }
}
