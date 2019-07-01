package me.amr.rest;

import io.swagger.annotations.Api;
import me.amr.exception.UnauthorizedException;
import me.amr.model.LoginObj;
import me.amr.model.User;
import me.amr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(value = "UserController", description = "User REST API", basePath = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public User login(@Valid @RequestBody LoginObj obj) {
        return userService.login(obj);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }
}
