package model;

import javax.persistence.*;

@Entity
@Table(name = "authorization")
public class Authorization {
    @Id
    @Column(name = "fk_users_id")
    private int id;

    private String token;

    public Authorization() {
    }
}
