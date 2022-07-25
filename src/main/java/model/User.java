package model;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "pk_users_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;

    public User() {}


}
