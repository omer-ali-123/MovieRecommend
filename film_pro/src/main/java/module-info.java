module org.example.film_pro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.film_pro to javafx.fxml;
    exports org.example.film_pro;
}