package com.fox.storey.util;

import com.fox.storey.service.JwtService;
import com.fox.storey.service.ProductService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(Mockito.mock(AuthenticationProvider.class));
        return http.build();
    }

    @Bean
    public ProductService productService() {
        return Mockito.mock(ProductService.class);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

    @Bean
    public JwtService jwtService() {
        return Mockito.mock(JwtService.class);
    }
}