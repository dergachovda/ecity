package ua.org.ecity.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.org.ecity.entities.*;
import ua.org.ecity.repository.UserRepository;
import ua.org.ecity.services.CityService;
import ua.org.ecity.services.RegionService;
import ua.org.ecity.services.UserRolesService;
import ua.org.ecity.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    CityService cityService;

    @Autowired
    RegionService regionService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRolesService userRolesService;

    /*
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot";
    }
    */

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public GameStatus userRegister(@RequestParam("login") String login,
                                   @RequestParam("password") String password,
                                   @RequestParam("email") String email,
                                   @RequestParam("firstName") String name,
                                   @RequestParam("lastName") String lastame,
                                   @RequestParam("cityLive") String cityLive) {
        return userService.enterNewUserInDB(login, password, email, name, lastame, cityLive);

//        if (userService.getUser(login) != null) {
//            return GameStatus.USER_EXIST;
//        }
//
//        if ((login.isEmpty())) {
//            return GameStatus.USER_DOESNT_ENTER_LOGIN;
//        }
//
//        if (!userService.isEmailValid(email)) {
//            return GameStatus.USER_ENTER_INCORRECT_EMAIL;
//        }
//
//        if (!(password.isEmpty())) {
//            User user = new User();
//            user.setLogin(login);
//            user.setPassword(new BCryptPasswordEncoder().encode(password));
//            user.setEmail(email);
//            user.setUsername(name);
//            user.setLastname(lastame);
//            user.setCitylives(cityLive);
//            user.setEnable(true);
//            userRepository.save(user);
//
//            userRolesService.saveUserRole(new UserRoles(user.getId(), 1));
//            return GameStatus.USER_REGISTER_OK;
//        }
//
//
//        return GameStatus.USER_PASSWORD_INCORECT;
    }

    @RequestMapping("/city")
    public
    @ResponseBody
    List<City> city(@RequestParam(value = "name") String name) {
        logger.info("/city 'name': " + name);
        return cityService.getCitiesByName(name);
    }

    @RequestMapping("/city/{id}")
    public Object cityInfo(@PathVariable("id") int id) {
        if (cityService.checkCityNotInBase(id)) {
            return new AdminPanelResult(AdminPanelStatus.CITY_NOT_FOUND, id);
        }
        return cityService.getCityByID(id);
    }

    @RequestMapping("/city/region/{id}")
    public Object getCityByRegionId(@PathVariable("id") int id) {
        if (cityService.checkCityNotInBase(id)) {
            return new AdminPanelResult(AdminPanelStatus.CITY_NOT_FOUND, id);
        }
        return cityService.getCitiesByRegionId(id);
    }

    @RequestMapping("/cities")
    public List<City> cities() {
        return cityService.getCities();
    }

    @RequestMapping("/regions")
    public List<Region> regions() {
        return regionService.getRegions();
    }

    @RequestMapping("/region")
    public
    @ResponseBody
    List<Region> regionByName(@RequestParam(value = "name") String name) {
        logger.info("/region 'name': " + name);
        return regionService.getRegionByName(name);
    }

    @RequestMapping("/names")
    public List<Name> names() {
        List<City> cities = cityService.getCities();
        List<Name> names = new ArrayList<>();
        for (City city : cities) {
            Name name = new Name();
            name.setId(city.getId());
            name.setName(city.getName());
            names.add(name);
        }
        return names;
    }

    @RequestMapping("/user/hello")
    public List<City> hello() {
        return cityService.getCitiesByName("Одесса");
    }


    @RequestMapping("/another")
    public String another() {
        return "Another one";
    }

}
