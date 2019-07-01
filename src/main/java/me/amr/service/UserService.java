package me.amr.service;

import me.amr.model.LoginObj;
import me.amr.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getUserList();

    User login(LoginObj obj);

    User addUser(User user);

    User getCurrentUser();

    boolean isValidToken(String token);
}
