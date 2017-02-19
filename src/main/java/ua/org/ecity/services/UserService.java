package ua.org.ecity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.org.ecity.entities.GameStatus;
import ua.org.ecity.entities.Records;
import ua.org.ecity.entities.User;
import ua.org.ecity.entities.UserRoles;
import ua.org.ecity.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesService userRolesService;

    public User getUser(String login) {
        return userRepository.getByLogin(login);
    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();
        return matches;

    }

    public List<Records> gameRecords() {
        List<User> users = userRepository.findAll();
        List<Records> recordses = new ArrayList<Records>();
        for (User user : users) {
            recordses.add(new Records(user.getLogin(), user.getCounterwin(), user.getCounterloss()));
        }

        return recordses;
    }


    public GameStatus enterNewUserInDB(String login, String password, String email, String name, String lastame, String cityLive) {
        if (getUser(login) != null) {
            return GameStatus.USER_EXIST;
        }

        if ((login.isEmpty())) {
            return GameStatus.USER_DOESNT_ENTER_LOGIN;
        }

        if (login.length() > 20) {
            return GameStatus.USER_LOGIN_MUST_BE_LESS_20;
        }

        if (!isEmailValid(email)) {
            return GameStatus.USER_ENTER_INCORRECT_EMAIL;
        }

        if (!(password.isEmpty())) {
            User user = new User();
            user.setLogin(login);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setEmail(email);
            user.setUsername(name);
            user.setLastname(lastame);
            user.setCitylives(cityLive);
            user.setEnable(true);
            userRepository.save(user);

            userRolesService.saveUserRole(new UserRoles(user.getId(), 1));
            return GameStatus.USER_REGISTER_OK;
        }


        return GameStatus.USER_PASSWORD_INCORECT;
    }

    public User findUserByEmail(String userEmail) {
        return userRepository.getByEmail(userEmail);
    }

}
