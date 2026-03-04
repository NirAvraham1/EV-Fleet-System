package com.example.auth_service.service;

import com.example.auth_service.dto.AuthResponse;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // הרשמה (Register)
    public AuthResponse register(RegisterRequest request) {
        // 1. יצירת משתמש חדש עם סיסמה מוצפנת
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        
        // 2. שמירה ב-DB
        userRepository.save(user);

        // 3. יצירת טוקן עם UserID ו-Role (כמו שנדרש במטלה)
        return generateAuthResponse(user);
    }

    // התחברות (Login)
    public AuthResponse login(LoginRequest request) {
        // 1. אימות שם משתמש וסיסמה מול ה-DB
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. שליפת המשתמש המלא כדי לקבל את ה-ID וה-Role שלו
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        // 3. יצירת טוקן
        return generateAuthResponse(user);
    }

    // פונקציית עזר ליצירת הטוקן עם הנתונים הנוספים (Claims)
    private AuthResponse generateAuthResponse(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getRole().toString());

        // המרה ל-UserDetails ש-Spring Security מכיר
        var userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );

        var jwtToken = jwtService.generateToken(extraClaims, userDetails);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}