package me.amr.jwt;

import me.amr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    private static final String HEADER_AUTH = "Authorization";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HEADER_AUTH);

        logger.debug(request.getRequestURI());


        if (token != null && userService.isValidToken(token)){
            return true;
        }

        throw new RuntimeException("unauthorized");
    }
}
