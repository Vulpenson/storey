package com.fox.storey.service;

import com.fox.storey.config.EncoderConfig;
import com.fox.storey.entity.User;
import com.fox.storey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final UserRepository repository;
    private final EncoderConfig encoderConfig;

    // Method to load user details by username (email)
    @Override
    public UserInfoDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();
        return new UserInfoDetails(user);
    }

    // Add any additional methods for registering or managing users
    public String addUser(User userInfo) {
        // Encrypt password before saving
        userInfo.setPassword(encoderConfig.passwordEncoder().encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully!";
    }
}