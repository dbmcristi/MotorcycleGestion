package networkcsharp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User extends Entity<Integer> implements Serializable {
    private int id;
    private String password;
    private String userName;
    private static final long serialVersionUID = 1L;
    public User(int id, String password, String userName) {
        super(id);
        this.id = id;
        this.password = password;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                '}' + "\n";
    }

    public void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(id);
        stream.writeObject(password);
        stream.writeObject(userName);
    }

    public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        id = (int) stream.readObject();
        password = (String) stream.readObject();
        userName = (String) stream.readObject();
    }
}
