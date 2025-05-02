package com.lorenipson.user_service.service;

import com.lorenipson.user_service.dto.LoginRequest;
import com.lorenipson.user_service.dto.LoginResponse;
import com.lorenipson.user_service.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public LoginService(AuthenticationManager authenticationManager,
                        JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetailsImpl user = (UserDetailsImpl) authenticate.getPrincipal();
        String jwt = jwtService.createLoginAccessToken(user);

        return new LoginResponse(
                jwt,
                user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                user.getAge()
        );

    }

}
