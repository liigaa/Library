import library.Book;
import library.Library;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class Main {
    Library myLibrary = new Library();
    ImageIcon icon = new ImageIcon("src/library.jpg");
    public static void main(String[] args) throws SQLException {
        Main main = new Main();
        main.menu();
    }

    void menu() throws SQLException {
        String libraryOption;

        do {
            String[] options = {
                    "Select...", "Add book", "View all books", "Borrow book", "Return book","View borrowed books","Update book",
                    "Delete book","Add User", "Exit"};
            libraryOption = (String) JOptionPane.showInputDialog(null, """
                            Welcome to Library!
                            Please select choice
                            Choose EXIT to quit""",
                    "Library menu",
                    JOptionPane.PLAIN_MESSAGE,
                    icon,
                    options,
                    options[0]);
            switch (libraryOption) {
                case "Add book":
                    JOptionPane.showMessageDialog(null, myLibrary.addBook());
                    break;
                case "View all books":
                    List<String> bookStrings = myLibrary.getBooks().stream().map(Book::toString).toList();
                    JOptionPane.showMessageDialog(null, String.join(",\n", bookStrings), "All Books", JOptionPane.PLAIN_MESSAGE);
                    break;
                case "Delete book":
                    JOptionPane.showMessageDialog(null, myLibrary.removeBook());
                    break;
                case "Add User":
                    JOptionPane.showMessageDialog(null, myLibrary.addUser());
                    break;
                case "Borrow book":
                    JOptionPane.showMessageDialog(null, myLibrary.borrowing());
                    break;
                case "View borrowed books":
                    JOptionPane.showMessageDialog(null, myLibrary.borrowedByUser());
                    break;
                case "Return book":
                    JOptionPane.showMessageDialog(null, myLibrary.returning());
                    break;
                case "Update book":
                    JOptionPane.showMessageDialog(null, myLibrary.updateBook());
                    break;
                default:
                    break;
            }
        } while (!libraryOption.equals("Exit"));
    }

    }

