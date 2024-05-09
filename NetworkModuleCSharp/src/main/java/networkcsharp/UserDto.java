package networkcsharp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

public class UserDto implements IRequest, IResponse, Serializable {
    private HashSet<String> username;
    private String message;
    private static final long serialVersionUID = 1L;
    public UserDto(HashSet<String> username, String message) {
        System.out.println("UserDTO instantiated");
        this.username = username;
        this.message = message;
    }

    public UserDto() {
    }

    public HashSet<String> getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (String variable : username) {
            text.append(" ").append(variable);
        }
        return "UserDto{" +
                "username=" + text +
                '}';
    }

    public void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(username);
        stream.writeObject(message);
    }

    public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        username = (HashSet<String>) stream.readObject();
        message = (String) stream.readObject();
    }
}
