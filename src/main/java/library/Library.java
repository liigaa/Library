package library;

import database.DBBookRepository;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    DBBookRepository dbBookRepository = new DBBookRepository();
    public ArrayList<Book> books;
    private ArrayList<User> users;


    public Library(){
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public ArrayList<Book> getBooks() {
        return dbBookRepository.getAll();
    }

    public ArrayList<User> getUsers() {return dbBookRepository.getAllUser(); }

    public String addBook(){
        JTextField title = new JTextField(20);
        String[] availableGenre = {"Fantasy", "Romance", "Scientific", "Criminal", "Children's book"};
        JComboBox<String> genreField = new JComboBox<>(availableGenre);
        genreField.setVisible(true);
        String genre = (String) genreField.getSelectedItem();
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Title:"));
        myPanel.add(title);
        myPanel.add(new JLabel("Genre: "));
        myPanel.add(genreField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION) {
            return "No new Book!";
        }
        if (result == JOptionPane.OK_OPTION) {
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter book quantity", "Add book", JOptionPane.QUESTION_MESSAGE));

            Book book = new Book(title.getText(), genre, quantity);
            String newGenre = (String) genreField.getSelectedItem();
            book.setGenre(newGenre);
            if (title.getText() == null) {
                return "Please add Title";
            }
          dbBookRepository.createBook(book);
        }
        return "Book: " + title.getText() + " added successfully";
    }
    public String addUser(){
        JTextField name = new JTextField(20);
        JTextField email = new JTextField(20);
        JTextField password = new JTextField(20);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name:"));
        myPanel.add(name);
        myPanel.add(new JLabel("e-mail:"));
        myPanel.add(email);
        myPanel.add(new JLabel("Password: "));
        myPanel.add(password);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION) {
            return "No new User!";
        }
        if (result == JOptionPane.OK_OPTION) {
            User user = new User(name.getText(),email.getText(),password.getText());
//
//            if (user.getName() == null || user.getName().isEmpty()) {
//                return "Please add Name";
//            }
//            if (user.getEmail() == null || user.getEmail().isEmpty()) {
//                return "Please add email";
//            }
//            if (user.getPassword() == null || user.getPassword().isEmpty()) {
//                return "Please add password";
//            }
            dbBookRepository.createUser(user);
        }
        return "User: " + name.getText() + " added successfully";
    }
    public String removeBook() {
        try {
            ArrayList<Book> books = this.getBooks();
            Book bookToRemove = (Book) JOptionPane.showInputDialog(null,
                    "Choose Book to delete",
                    "Delete Book",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    books.toArray(new Book[0]),
                    books);
            int id = bookToRemove.getId();
            dbBookRepository.deleteBook(id);

            return "Book: " + bookToRemove.getTitle() + " deleted successfully";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No book to delete",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
        }
        return "Choose Book to delete";
    }
    public boolean borrowedBook(User user,Book book) throws SQLException {
            if (dbBookRepository.getBorrowedBook(user).contains(book)) {
                return true;
            }
        return false;
    }
    public String borrowing(){
        try {
            ArrayList<User> users = this.getUsers();
            User user1 = (User) JOptionPane.showInputDialog(null,
                    "Choose User which borrow",
                    "Borrow Book",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    users.toArray(new User[0]),
                    users);
            ArrayList<Book> books = this.getBooks();
            Book bookToBorrow = (Book) JOptionPane.showInputDialog(null,
                    "Choose Book to borrow",
                    "Borrow Book",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    books.toArray(new Book[0]),
                    books);

            if(bookToBorrow.getAvailableQuantity()!=0){
                if(dbBookRepository.getBorrowedBook(user1).size()<5){
                    if (dbBookRepository.getBorrowedBook(user1).contains(bookToBorrow)) {
                    dbBookRepository.borrowBook(bookToBorrow, user1);
                    bookToBorrow.updateQuantityWhenBorrow(1);
                    dbBookRepository.updateQuantity(bookToBorrow);
                    return user1.getName() + " borrowed book: " + bookToBorrow.getTitle();

                }else {
                        JOptionPane.showMessageDialog(null, "Book " + bookToBorrow.getTitle() + " already borrowed. Please choose another book", "Borrow book",
                                JOptionPane.WARNING_MESSAGE);

            }}else {JOptionPane.showMessageDialog(null, "Already borrowed five books!", "Borrow book",
                        JOptionPane.WARNING_MESSAGE);

            }}else JOptionPane.showMessageDialog(null, "Book " + bookToBorrow.getTitle() + " not available. Please choose another book", "Borrow book",
                    JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No book to borrow",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
        }
       return "Start again!";
    }
    public String returning(){
        try {
            ArrayList<User> users = this.getUsers();
            User user1 = (User) JOptionPane.showInputDialog(null,
                    "Choose User which returning",
                    "Return Book",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    users.toArray(new User[0]),
                    users);

            ArrayList<Book> books = dbBookRepository.getBorrowedBook(user1);
            Book bookToBorrow = (Book) JOptionPane.showInputDialog(null,
                    "Choose Book to return",
                    "Return Book",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    books.toArray(new Book[0]),
                    books);
            dbBookRepository.returnBook(bookToBorrow, user1);
            bookToBorrow.updateQuantityWhenReturn(1);
            dbBookRepository.updateQuantity(bookToBorrow);
            return user1.getName() + " returned book: " + bookToBorrow.getTitle();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No book to return",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
        }
        return "Start again!";
    }
    public String borrowedByUser() throws SQLException {
            ArrayList<User> users = this.getUsers();
            User user1 = (User) JOptionPane.showInputDialog(null,
                    "Choose User",
                    "Borrowed Books",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    users.toArray(new User[0]),
                    users);
            List<String> bookStrings = dbBookRepository.getBorrowedBook(user1).stream().map(Book::toString).toList();
           JOptionPane.showMessageDialog(null, String.join(",\n", bookStrings), "All borrowed book by: " + user1.getName(), JOptionPane.PLAIN_MESSAGE);

        return "Return to menu!";
    }
    public String updateBook(){
        try {
            ArrayList<Book>books = this.getBooks();
            Book bookToUpdate = (Book) JOptionPane.showInputDialog(null, "Choose Book to update",
                    "Update Book",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    books.toArray(new Book[0]), books);
            JTextField titleField = new JTextField(20);
            titleField.setText(bookToUpdate.getTitle());
            String[] availableGenre = {"Fantasy", "Romance", "Scientific", "Criminal", "Children's book"};
            JComboBox<String> genreField = new JComboBox<>(availableGenre);
            genreField.setVisible(true);
            genreField.setSelectedItem(bookToUpdate.getGenre());
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Title:"));
            myPanel.add(titleField);
            myPanel.add(new JLabel("Genre: "));
            myPanel.add(genreField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Update Book: " + bookToUpdate.getTitle(), JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION) {
                return "Update canceled!";
            }
            if (result == JOptionPane.OK_OPTION) {
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "There are " + bookToUpdate.getTitle()+ " " + bookToUpdate.getAvailableQuantity() + " books. Please enter amount to add. ",
                        "Update book", JOptionPane.QUESTION_MESSAGE));
                String newTitle = titleField.getText();
                String newGenre = (String) genreField.getSelectedItem();
                bookToUpdate.setTitle(newTitle);
                bookToUpdate.setGenre(newGenre);
                bookToUpdate.updateQuantityWhenReturn(quantity);
                dbBookRepository.updateBook(bookToUpdate);
                //dbBookRepository.updateBorrowedBook(bookToUpdate);
            }
            return "Book: " + bookToUpdate.getTitle() + " updated successfully!";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No book to update",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
        }
        return "Choose Book to update";
        }
    }

