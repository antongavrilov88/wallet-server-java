package model;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String pass;
    @Column(name = "is_admin")
    private boolean isAdmin = false;

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }



    public boolean isAdmin() {
        return isAdmin;
    }

    public User(String email, String password) {
        this.email = email;
        this.pass = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
