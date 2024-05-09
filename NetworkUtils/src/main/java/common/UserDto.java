package common;

import java.io.Serializable;
import java.util.Set;

public class UserDto implements Serializable {

    private final Set<String> username;
    private final String message;
    public UserDto(Set<String> username, String message) {
        this.username = username;
        this.message = message;
    }

    public Set<String> getUsernames() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username=" + username +
                '}';
    }
}
