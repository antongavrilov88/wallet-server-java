package model;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "usertoken")
    private String userToken;

    @Column(name = "users_id")
    private int usersId;

    @Column(name = "is_active")
    private boolean isActive;

    public Token() {
    }

    public Token(String userToken, int usersId) {
        this.usersId = usersId;
        this.userToken = userToken;
    }

    public Token(String userToken, int usersId, boolean isActive) {
        this.userToken = userToken;
        this.usersId = usersId;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getUserToken() {
        return userToken;
    }
}
