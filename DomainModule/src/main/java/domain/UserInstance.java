package domain;

public class UserInstance {

    private static UserInstance instance;
    private boolean isLogged;

    private String user;

    private UserInstance(){}

    public static UserInstance getInstance() {
        if (instance == null) {
            instance = new UserInstance();
        }
        return instance;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
