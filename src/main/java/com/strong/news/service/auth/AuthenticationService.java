package com.strong.news.service.auth;

import com.strong.news.model.auth.AuthenticationRequest;
import com.strong.news.model.auth.AuthenticationResponse;
import com.strong.news.model.auth.RegisterRequest;
import com.strong.news.model.Role;
import com.strong.news.model.User;
import com.strong.news.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepo repo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder().email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
