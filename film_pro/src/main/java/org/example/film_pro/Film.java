package org.example.film_pro;

import javafx.scene.image.Image;

public class Film {
    private int id_film;
    private String category;
    private String name;
    private String photoName;
    private Image photoImg;

    public Film(int id_film, String category, String name, String photoName,Image photoImg) {
        this.id_film = id_film;
        this.category = category;
        this.name = name;
        this.photoName = photoName;
        this.photoImg = photoImg;
    }
    public Film(){

    }

    public Image getPhotoImg() {
        return photoImg;
    }

    public void setPhotoImg(Image photoImg) {
        this.photoImg = photoImg;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
