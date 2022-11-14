package model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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

    @Column(name = "email")
    private String email;
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "is_admin")
    private boolean isAdmin;





    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}



