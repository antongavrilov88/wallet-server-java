package model;



import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.persistence.*;
import java.io.Serializable;

@Schema(description = "User entity")
@Component
@Entity
@Table(name = "users")
@ResponseBody
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)

    private int id;


    private String email;
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    private String pass;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "is_admin")
    private boolean isAdmin;

    public void getIsAdmin(boolean admin) {
        isAdmin = admin;
    }



    public boolean getIsAdmin() {
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
