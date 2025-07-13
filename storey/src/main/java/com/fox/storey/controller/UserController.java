package com.fox.storey.controller;

import com.fox.storey.dto.UserDto;
import com.fox.storey.dto.AuthRequest;
import com.fox.storey.service.JwtService;
import com.fox.storey.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserInfoService service;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserDto userDto) {
        return service.addUser(userDto);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", authRequest.username(), e);
            throw new RuntimeException(e);
        }
        log.info("Authentication successful: {}", authentication);
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            log.info("Authentication failed: {}", authentication);
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}
