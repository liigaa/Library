package library;

public class Book {
    private int id;
    private String title;
    private String genre;
    private int availableQuantity;

    public Book(int id, String title, String genre, int availableQuantity) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.availableQuantity = availableQuantity;
    }

    public Book(String title, String genre, int availableQuantity) {
        this.title = title;
        this.genre = genre;
        this.availableQuantity = availableQuantity;
    }

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    public void updateQuantityWhenBorrow(int borrowedQuantity){
       this.availableQuantity -=borrowedQuantity;
    }
    public void updateQuantityWhenReturn(int borrowedQuantity){
        this.availableQuantity += borrowedQuantity;
    }

    @Override
    public String toString() {
        return "Book " +
                "id: " + id +
                "; Title: " + title +
                "; Genre: " + genre +
                "; Available quantity: " + availableQuantity;
    }
}
