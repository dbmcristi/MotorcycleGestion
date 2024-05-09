package domain;

public class User extends Entity<Integer> {

  //  int id;
    String username;
    String password;


    public User(int id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
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
        return "domain.User{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                '}';
    }
}
