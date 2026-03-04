package com.example.fleet_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // טריק: במקום לחפש בדאטה-בייס, אנחנו מחזירים תמיד תשובה חיובית
        // אם הטוקן תקין (החתימה שלו נבדקה כבר), אנחנו מאשרים את המשתמש
        return username -> new User(username, "password", Collections.emptyList());
    }
}