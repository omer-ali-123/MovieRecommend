package org.example.film_pro;

import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    String sqlUser = "root"; //SQL login datas
    String sqlPassword = "12345";
    String sqlUrl = "jdbc:mysql://localhost:3306/film_pro";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(sqlUrl, sqlUser, sqlPassword);
    }

    public void showError(SQLException ex) {
        System.out.println("Error" + ex.getMessage());
        System.out.println("Error Code:" + ex.getErrorCode());
    }

    public ArrayList<User> sqlSelectUsers() throws SQLException {

        Connection connection = null;
        Statement statement;
        ResultSet rs;
        ArrayList<User> users = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from film_pro.users");
            users = new ArrayList<User>();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id_user"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("password")));
            }

        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        System.out.println(users);
        return users;
    }
    public User sqlSelectUser(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null;
        try {
            //Checking SQL with user's username and password
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.users where username = ? and password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id_user"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return user;
    }
    public User sqlCheckUsername(String username) throws SQLException {
        //Checking username  is tooken or not
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.users where username = ?");
            statement.setString(1, username);
            rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id_user"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return user;
    }
    public int sqlInsertUser(User u) throws SQLException {
        //Inserting user to SQL
        Connection connection = null;
        PreparedStatement pStatement = null;
        int kontrol = 0;
        try {
            connection = getConnection();

            String sqlCode = "insert into film_pro.users(id_user, name, surname, username,password)"
                    + "values(?,?,?,?,?)";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, u.getId());
            pStatement.setString(2, u.getName());
            pStatement.setString(3, u.getSurname());
            pStatement.setString(4, u.getUsername());
            pStatement.setString(5, u.getPassword());
            kontrol = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return kontrol;
    }

    public LinkedList sqlSelectFilms() throws SQLException {
        //Getting all movies
        Connection connection = null;
        Statement statement;
        ResultSet rs;
        LinkedList films = new LinkedList();
        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from film_pro.films");
            while (rs.next()) {
                //Getting the image from sql
                InputStream is = rs.getBinaryStream("photo");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] content = new byte[1024];
                int size;
                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                Image newImage = new Image("file:photo.jpg", 100, 150, true, true);

                films.add(new Film(
                        rs.getInt("id_film"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getString("photoName"),
                        newImage)
                );
            }
        } catch (SQLException exception) {
            showError(exception);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return films;
    }
    public Film sqlSelectFilm(int id) throws SQLException {
        //Getting wanted movie
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Film film = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.films where id_film = ?");
            statement.setInt(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {

                //Getting the image from sql
                InputStream is = rs.getBinaryStream("photo");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] content = new byte[1024];
                int size;
                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                Image newImage = new Image("file:photo.jpg", 100, 150, true, true);


                film = new Film(
                        rs.getInt("id_film"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("photoName"),
                        newImage
                );
            }
        } catch (SQLException exception) {
            showError(exception);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return film;
    }
    public Film sqlSelectFilmName(String name_film) throws SQLException {
        //Getting wanted movie
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Film film = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.films where film_pro.films.name = ?");
            statement.setString(1, name_film);
            rs = statement.executeQuery();
            if (rs.next()) {

                //Getting the image from sql
                InputStream is = rs.getBinaryStream("photo");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] content = new byte[1024];
                int size;
                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                Image newImage = new Image("file:photo.jpg", 100, 150, true, true);


                film = new Film(
                        rs.getInt("id_film"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("photoName"),
                        newImage
                );
            }
        } catch (SQLException exception) {
            showError(exception);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return film;
    }

    public int sqlDeleteFilm(int id) throws SQLException{
        //Deleting wanted movie from SQL
        Connection connection = null;
        PreparedStatement pStatement = null;
        int effected = 0;
        try {
            connection = getConnection();
            String sqlCode = "delete from film_pro.films where id_film = ?";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, id);
            effected = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        }
        finally{
            if (connection != null || pStatement != null) {
                pStatement.close();
                connection.close();
            }
        }
        try {
            connection = getConnection();
            String sqlCode = "delete from film_pro.films_disliked where id_film = ?";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, id);
            effected = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        }
        finally{
            if (connection != null || pStatement != null) {
                pStatement.close();
                connection.close();
            }
        }
        try {
            connection = getConnection();
            String sqlCode = "delete from film_pro.films_liked where id_film = ?";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, id);
            effected = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        }
        finally{
            if (connection != null || pStatement != null) {
                pStatement.close();
                connection.close();
            }
        }
        return effected;
    }
    public StackList<Liked> sqlSelectLikes(int id_user) throws SQLException {

        Connection connection = null;
        PreparedStatement pStatement;
        ResultSet rs;
        StackList<Liked> likes = null;
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement("select * from film_pro.films_liked where id_user = ?");
            pStatement.setInt(1,id_user);
            rs = pStatement.executeQuery();
            likes = new StackList<>();
            while (rs.next()) {
                likes.add(new Liked(
                        rs.getInt("id_liked"),
                        rs.getInt("id_film"),
                        rs.getInt("id_user")));
            }

        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return likes;
    }
    public StackList<Disliked> sqlSelectDislikes(int id_user) throws SQLException {

        Connection connection = null;
        PreparedStatement pStatement;
        ResultSet rs;
        StackList<Disliked> dislikes = null;
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement("select * from film_pro.films_disliked where id_user = ?");
            pStatement.setInt(1,id_user);
            rs = pStatement.executeQuery();
            dislikes = new StackList<>();
            while (rs.next()) {
                dislikes.add(new Disliked(
                        rs.getInt("id_disliked"),
                        rs.getInt("id_film"),
                        rs.getInt("id_user")));
            }

        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return dislikes;
    }
    public int sqlDeleteLike(int id_film,int id_user) throws SQLException{
        Connection connection = null;
        PreparedStatement pStatement = null;
        int effected = 0;
        try {
            connection = getConnection();
            String sqlCode = "delete from film_pro.films_liked where id_film = ? and id_user = ?;";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, id_film);
            pStatement.setInt(2,id_user);
            effected = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        }
        finally{
            if (connection != null || pStatement != null) {
                pStatement.close();
                connection.close();
            }
        }
        return effected;
    }
    public int sqlDeleteDislike(int id_user,int id_film) throws SQLException{
        Connection connection = null;
        PreparedStatement pStatement = null;
        int effected = 0;
        try {
            connection = getConnection();
            String sqlCode = "delete from film_pro.films_disliked where id_film = ? and id_user = ?";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, id_film);
            pStatement.setInt(2,id_user);
            effected = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        }
        finally{
            if (connection != null || pStatement != null) {
                pStatement.close();
                connection.close();
            }
        }
        return effected;
    }
    public Liked sqlSelectLike(int id_film,int id_user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Liked like = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.films_liked where id_film = ? and id_user = ?");
            statement.setInt(1, id_film);
            statement.setInt(2, id_user);
            rs = statement.executeQuery();
            if (rs.next()) {
                like = new Liked(
                        rs.getInt("id_liked"),
                        rs.getInt("id_film"),
                        rs.getInt("id_user")
                );
            }
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return like;
    }
    public Disliked sqlSelectDislike(int id_film, int id_user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Disliked dislike = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.films_disliked where id_film = ? and id_user = ?");
            statement.setInt(1, id_film);
            statement.setInt(1, id_user);
            rs = statement.executeQuery();
            if (rs.next()) {
                dislike = new Disliked(
                        rs.getInt("id_disliked"),
                        rs.getInt("id_film"),
                        rs.getInt("id_user")
                );
            }
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return dislike;
    }
    public int sqlInsertLike(int id_film,int id_user) throws SQLException{
        //Inserting like to SQL
        Connection connection = null;
        PreparedStatement pStatement = null;
        int kontrol = 0;
        try {
            connection = getConnection();
            String sqlCode = "insert into film_pro.films_liked(id_liked, id_film, id_user) values(?,?,?)";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, 0);
            pStatement.setInt(2, id_film);
            pStatement.setInt(3, id_user);
            kontrol = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return kontrol;
    }
    public int sqlInsertDislike(int id_film,int id_user) throws SQLException{
        //Inserting like to SQL
        Connection connection = null;
        PreparedStatement pStatement = null;
        int kontrol = 0;
        try {
            connection = getConnection();
            String sqlCode = "insert into film_pro.films_disliked(id_disliked, id_film, id_user) values(?,?,?)";
            pStatement = connection.prepareStatement(sqlCode);
            pStatement.setInt(1, 0);
            pStatement.setInt(2, id_film);
            pStatement.setInt(3, id_user);
            kontrol = pStatement.executeUpdate();
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return kontrol;
    }
    public boolean sqlCheckLike(int id_film,int id_user) throws SQLException {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Liked like = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.films_liked where id_film = ? and id_user = ?");
            statement.setInt(1, id_film);
            statement.setInt(2, id_user);
            rs = statement.executeQuery();
            if (rs.next()) {
                flag = true;
            }
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return flag;
    }
    public boolean sqlCheckDisike(int id_film,int id_user) throws SQLException {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Disliked like = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from film_pro.films_disliked where id_film = ? and id_user = ?");
            statement.setInt(1, id_film);
            statement.setInt(2, id_user);
            rs = statement.executeQuery();
            if (rs.next()) {
                flag = true;
            }
        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return flag;
    }
    public List<Category> sqlSelectLikedCategories(int id_user) throws SQLException {
        Connection connection = null;
        PreparedStatement pStatement;
        ResultSet rs;
        List<Category> categories = new ArrayList<>();
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement("SELECT count(film_pro.films.category) as counter,film_pro.films.category\n" +
                    "FROM film_pro.films_liked\n" +
                    "INNER JOIN film_pro.films\n" +
                    "ON film_pro.films_liked.id_film = film_pro.films.id_film\n" +
                    "WHERE film_pro.films_liked.id_user = ?\n" +
                    "group by film_pro.films.category;");
            pStatement.setInt(1,id_user);
            rs = pStatement.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                        rs.getString("category"),
                        rs.getInt("counter")));
            }

        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return categories;
    }
    public List<Category> sqlSelectDislikedCategories(int id_user) throws SQLException {
        Connection connection = null;
        PreparedStatement pStatement;
        ResultSet rs;
        List<Category> categories = new ArrayList<>();
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement("SELECT count(film_pro.films.category) as counter,film_pro.films.category\n" +
                    "FROM film_pro.films_disliked\n" +
                    "INNER JOIN film_pro.films\n" +
                    "ON film_pro.films_disliked.id_film = film_pro.films.id_film\n" +
                    "WHERE film_pro.films_disliked.id_user = ?\n" +
                    "group by film_pro.films.category;");
            pStatement.setInt(1,id_user);
            rs = pStatement.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                        rs.getString("category"),
                        rs.getInt("counter")));
            }

        } catch (SQLException exception) {
            showError(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return categories;
    }
    //This function will buildQuery
    private String buildQuery(int numberOfCategories) {
        StringBuilder query = new StringBuilder("SELECT * FROM film_pro.films WHERE category IN (");
        for (int i = 0; i < numberOfCategories; i++) {
            query.append("?");
            if (i < numberOfCategories - 1) {
                query.append(", ");
            }
        }
        query.append(")");
        return query.toString();
    }
    public LinkedList<Film> executeQuery(List<String> categories) {
        LinkedList<Film> films = new LinkedList<>();
        if (categories == null || categories.isEmpty()) {
            return null;
        }
        String query = buildQuery(categories.size());

        // Execute the query
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the category values in the prepared statement
            for (int i = 0; i < categories.size(); i++) {
                stmt.setString(i + 1, categories.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //Getting the image from sql
                InputStream is = rs.getBinaryStream("photo");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] content = new byte[1024];
                int size;
                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                Image newImage = new Image("file:photo.jpg", 100, 150, true, true);

                films.add(new Film(
                        rs.getInt("id_film"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getString("photoName"),
                        newImage)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return films;
    }
}
