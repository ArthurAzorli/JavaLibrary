package br.edu.usp.javalibrary.javalibrary.service.repository;

import br.edu.usp.javalibrary.javalibrary.exceptions.FileLoadException;
import br.edu.usp.javalibrary.javalibrary.service.domains.User;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@Singleton
public class UserRepository {
    static final String userFilePath = "users.json";

    @Inject
    private JsonService jsonService;
    private ArrayList<User> users;


    private void loadUsers() {
        try {
            users = jsonService.loadJson(userFilePath);
        } catch (FileLoadException e) {
            users = new ArrayList<>();
            throw e;
        }
    }

    public ArrayList<User> getUsers() {
        if (users == null) loadUsers();
        return users;
    }

    public Optional<User> getUser(String emailAddress) {
        return getUsers().stream()
                .filter(user -> user.getEmailAddress().equals(emailAddress))
                .findFirst();
    }

    public Optional<User> getUser(UUID id) {
        return getUsers().stream()
                .filter(user -> user.getID().equals(id))
                .findFirst();
    }


    public void saveUser(User user) {
        final ArrayList<User> users = getUsers();
        if (!users.contains(user)) {
            users.add(user);
        } else {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getID() == user.getID()) {
                    users.set(i, user);
                }
            }
        }
        saveUsers();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        saveUsers();
    }

    public void saveUsers() {
        jsonService.saveJson(userFilePath, users);
    }


}
