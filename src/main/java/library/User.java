package library;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean verifyPassword(String password){
        if(password.equals(this.password)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "User id: " + id +
                "; Name: " + name +
                "; email: " + email;
    }
}
