package ua.org.ecity.repository;

import org.springframework.data.repository.CrudRepository;
import ua.org.ecity.entities.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User getByLogin(String login);

    User save(User user);

    User findById(int id);

    List<User> findAll();


    User getByEmail(String userEmail);
}
