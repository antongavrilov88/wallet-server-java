package model;



import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity
@Table(name = "users")
public class User implements Serializable {

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

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = String.valueOf(pass.hashCode());
    }
}
