package database;
import library.Book;
import library.User;
import java.sql.*;
import java.util.ArrayList;

public class DBBookRepository {
    DBConnection dbConnection = new DBConnection();


    public void createBook(Book book){
        String query = "INSERT INTO book(title, genre, quantity) VALUES(?,?,?)";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getGenre());
            preparedStatement.setInt(3, book.getAvailableQuantity());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void borrowBook(Book book, User user) {
        String query = "INSERT INTO borrowed_book(id, title, genre, id_user, name, status) VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.setString(5, user.getName());
           // preparedStatement.setDate(5, (java.sql.Date) new Date());
            preparedStatement.setString(6, "Borrowed");

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void returnBook(Book book, User user){
        String query = "UPDATE borrowed_book SET status=? WHERE id =? AND id_user =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, "Returned");
            preparedStatement.setInt(2, book.getId());
            preparedStatement.setInt(3, user.getId());

            // preparedStatement.setDate(5, (java.sql.Date) new Date());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateQuantity(Book book){
        String query = "UPDATE book SET quantity=? WHERE id =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, book.getAvailableQuantity());
            preparedStatement.setInt(2, book.getId());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Book> getBorrowedBook(User user) throws SQLException {
        String query = "SELECT id, title, genre FROM borrowed_book WHERE status='Borrowed' AND id_user=" + user.getId();
        ArrayList<Book> books = new ArrayList<>();

       try {
           Statement statement = dbConnection.getConnection().createStatement();

        ResultSet result = statement.executeQuery(query);
        while (result.next()){
            int book_id = result.getInt("id");
            String book_title = result.getString("title");
            String book_genre = result.getString("genre");

            books.add(new Book(book_id, book_title, book_genre,1));
        }
        }catch (SQLException e) {
           e.printStackTrace();
       }
        return books;
}
    public ArrayList<Book> getAll() {
        String query = "SELECT * FROM book";
        Statement statement;
        ArrayList<Book> books = new ArrayList<>();
        try {
            statement =dbConnection.getConnection().createStatement();

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int id = results.getInt("id");
                String title = results.getString("title");
                String genre = results.getString("genre");
                Integer quantity = results.getInt("quantity");

                Book book = new Book(id, title, genre, quantity);
               books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public void createUser(User user){
        String query = "INSERT INTO user(name, email, password) VALUES(?,?,?)";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<User> getAllUser() {
        String query = "SELECT * FROM user";
        Statement statement;
        ArrayList<User> users = new ArrayList<>();
        try {
            statement =dbConnection.getConnection().createStatement();

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int id = results.getInt("id_user");
                String name = results.getString("name");
                String email = results.getString("email");

                User user = new User(id, name, email);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public void updateBook(Book book){
        String query = "UPDATE book SET title=?, genre=?, quantity =? WHERE id=" +book.getId();
        String query1 = "UPDATE borrowed_book SET title=?, genre? WHERE id=" + book.getId();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getGenre());
            preparedStatement.setInt(3, book.getAvailableQuantity());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBorrowedBook(Book book){
        String query = "UPDATE borrowed_book SET title=?, genre? WHERE id=" + book.getId();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getGenre());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int id){
        String query = "DELETE FROM book WHERE id=?";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = dbConnection.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
