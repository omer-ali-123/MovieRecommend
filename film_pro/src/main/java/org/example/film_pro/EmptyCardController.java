package org.example.film_pro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Optional;

public class EmptyCardController {

    @FXML
    private VBox box;

    @FXML
    private Button btn_substract;

    @FXML
    private ImageView filmImage;

    @FXML
    private Label lbl_filmCategory;

    @FXML
    private Label lbl_filmName;

    private int film_id;
    private String[] colors = {
            "#B9E5FF", "#BDB2FE", "#FB9AA8", "#FF5056", "#FFCC00", "#DFFF00", "#8CFF00", "#00FF00",
            "#00FF8C", "#00FFD8", "#00FFFF", "#00D8FF", "#008CFF", "#0000FF", "#8C00FF", "#D800FF",
            "#FF00FF", "#FF00D8", "#FF008C", "#FF0000", "#FF8C00", "#FFD800", "#FFFFFF", "#CCCCCC",
            "#999999", "#666666", "#333333", "#000000", "#FFCCCB", "#FFA07A", "#FA8072", "#E9967A",
            "#F08080", "#CD5C5C", "#DC143C", "#B22222", "#FF0000", "#8B0000", "#FF4500", "#FF6347",
            "#FF7F50", "#FFA500", "#FFD700", "#FFFF00", "#FFFFE0", "#FFFACD", "#FAFAD2", "#FFEFD5",
            "#FFE4B5", "#FFDAB9", "#EEE8AA", "#F0E68C", "#BDB76B", "#E6E6FA", "#D8BFD8", "#DDA0DD",
            "#DA70D6", "#EE82EE", "#FF00FF", "#BA55D3", "#9370DB", "#8A2BE2", "#9400D3", "#9932CC",
            "#8B008B", "#800080", "#4B0082", "#6A5ACD", "#483D8B", "#7B68EE", "#9370DB", "#00FF7F",
            "#7FFF00", "#7CFC00", "#ADFF2F", "#32CD32", "#00FF00", "#228B22", "#008000", "#006400",
            "#9ACD32", "#6B8E23", "#556B2F", "#66CDAA", "#8FBC8B", "#20B2AA", "#008B8B", "#008080",
            "#00CED1", "#40E0D0", "#48D1CC", "#AFEEEE", "#7FFFD4", "#B0E0E6", "#5F9EA0", "#4682B4",
            "#6495ED", "#00BFFF", "#1E90FF", "#ADD8E6", "#87CEEB", "#87CEFA", "#191970", "#000080",
            "#00008B", "#0000CD", "#4169E1", "#8A2BE2", "#4B0082", "#483D8B", "#6A5ACD", "#7B68EE"
    };
    public void setData(Film film){
        filmImage.setImage(film.getPhotoImg());
        lbl_filmName.setText(film.getName());
        lbl_filmCategory.setText(film.getCategory());
        box.setStyle("-fx-background-color: "+colors[(int)(Math.random()*colors.length)]);
        film_id = film.getId_film();
    }
    @FXML
    public void delete(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you really want to delete this film?");
        alert.setContentText("Click OK to confirm, or Cancel to go back.");

        // Customize the button types (Yes, Cancel)
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        // Show the confirmation dialog
        Optional<ButtonType> result = alert.showAndWait();

        // Check which button was clicked
        if (result.isPresent() && result.get() == buttonTypeYes) {

            DbHelper db = new DbHelper();
            AdminAddSubController panel = new AdminAddSubController();
            db.sqlDeleteFilm(film_id);

        }

    }

}