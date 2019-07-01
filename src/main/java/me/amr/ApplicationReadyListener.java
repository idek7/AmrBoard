package me.amr;

import me.amr.model.User;
import me.amr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${amr.admin.id}")
    private String adminId;

    @Value("${amr.admin.pw}")
    private String adminPw;

    @Value("${amr.admin.name}")
    private String adminName;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        User user = new User();
        user.setUserId(adminId);
        user.setPassword(adminPw);
        user.setUserName(adminName);
        userService.addUser(user);
    }
}
