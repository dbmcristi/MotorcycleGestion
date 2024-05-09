package myproject.hibernate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name="User")
@Entity
public class User implements IEntity<Integer> {
    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "id")
    Integer id;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;

    public User( String username, String password) {
//        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {

    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                '}';
    }


    @Override

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
