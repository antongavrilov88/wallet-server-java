package model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
public class User implements Serializable, UserDetails {


    public Set<? extends GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }


    @Transient
    private Set<? extends GrantedAuthority> grantedAuthorities;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @Column(name = "email")
    private String username;
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "is_admin")
    private boolean isAdmin;

    @JsonIgnore
    private boolean isAccountNonExpired;

    public User() {
        this.grantedAuthorities = new HashSet<>();
    }

    public User(int id,
                String username,
                String password,
                Set<? extends GrantedAuthority> grantedAuthorities,
                boolean isAdmin,
                boolean isAccountNonExpired,
                boolean isAccountNonLocked,
                boolean isCredentialsNonExpired,
                boolean isEnabled) {
        this.grantedAuthorities = grantedAuthorities;
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    @JsonIgnore
    private boolean isAccountNonLocked;

    @JsonIgnore
    private boolean isCredentialsNonExpired;

    @JsonIgnore
    private boolean isEnabled;


    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }





    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = String.valueOf(password.hashCode());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public void setAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
