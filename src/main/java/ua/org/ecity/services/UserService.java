package ua.org.ecity.services;

import ua.org.ecity.entities.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
