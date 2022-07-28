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

    public Token() {
    }

    public Token(String userToken, int usersId) {
        this.usersId = usersId;
        this.userToken = userToken;
    }

    public int getId() {
        return id;
    }

    public String getUserToken() {
        return userToken;
    }
}
