package com.strong.news.service.auth;

import com.strong.news.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {

    private final JwtService jwtService;

    @Autowired
    public LogoutService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);


        jwtService.expireToken(jwt);

    }

}

