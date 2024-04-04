package service;

import domain.Entity;
import domain.User;
import repository.RepositoryException;
import repository.UserRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class UserService {

    UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void add(User user1) throws RepositoryException {

        userRepo.add(user1);
    }

    public ArrayList<User> getAll()  {
        try {
            return userRepo.getAll();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastId() throws RepositoryException {
        var users = userRepo.getAll().stream()
                .sorted(Comparator.comparingInt(Entity::getId))
                .collect(Collectors.toList());
        Collections.reverse(users);
        if (users.isEmpty())
            return 0;
        return users.get(0).getId();
    }

    public boolean isLogged(String usrn, String pwsd) {
        try {
            return userRepo.isLogged(usrn,pwsd);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
