package org.example.film_pro;

public class Liked {
    private int id_liked;
    private int id_film;
    private int id_user;

    public Liked(int id_liked, int id_film, int id_user) {
        this.id_liked = id_liked;
        this.id_film = id_film;
        this.id_user = id_user;
    }

    public int getId_liked() {
        return id_liked;
    }

    public void setId_liked(int id_liked) {
        this.id_liked = id_liked;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}