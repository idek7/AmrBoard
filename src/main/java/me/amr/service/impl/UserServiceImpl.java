package me.amr.service.impl;

import com.google.common.collect.ImmutableList;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import me.amr.exception.UnauthorizedException;
import me.amr.model.LoginObj;
import me.amr.model.User;
import me.amr.repository.UserRepository;
import me.amr.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service("jwtService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private UserRepository userRepository;

    private User currentUser = null;

    @Override
    public List<User> getUserList() {
        return ImmutableList.copyOf(userRepository.findAll());
    }

    @Override
    public User login(LoginObj loginObj) {

        Optional<User> userOpt = userRepository.findById(loginObj.getUserId());
        logger.debug("user = {}", userOpt);

        if (!userOpt.isPresent()) {
            throw new UnauthorizedException("user not found");
        }

        User user = userOpt.get();
        if (BCrypt.checkpw(loginObj.getUserPW(), user.getPassword())) {
            currentUser = user;
            return updateToken(user);
        }

        throw new UnauthorizedException("invalid password");
    }

    @Override
    public User addUser(User user) {

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException(null);
        }
    }

    private User updateToken(User user) {
        String token = newToken(user);
        logger.debug("token = {}", token);
        user.setToken(token);

        userRepository.save(user);

        return user;
    }

    private String newToken(User user) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(user.getUserName())
                .setIssuer(jwtIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) // 10 days
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }
}
