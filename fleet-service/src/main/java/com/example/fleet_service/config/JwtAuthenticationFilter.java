package com.example.fleet_service.config;

import com.example.fleet_service.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. בדיקה אם יש בכלל טוקן ב-Header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // חיתוך המילה "Bearer "
        username = jwtService.extractUsername(jwt);

        // 2. אם יש משתמש והוא עדיין לא מאומת בקונטקסט
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // כאן אנחנו לא בודקים מול ה-DB (כי אין לנו את טבלת המשתמשים כאן!)
            // אנחנו סומכים על הטוקן בלבד.
            
            // שליפת התפקיד (Role) מתוך הטוקן
            String role = jwtService.extractClaim(jwt, claims -> claims.get("role", String.class));
            
            // יצירת רשימת הרשאות (חובה להוסיף ROLE_ להתחלה כדי ש-Spring Security יבין)
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + role)
            );

            // יצירת אובייקט האימות
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );
            
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            // שמירת האימות בקונטקסט של הבקשה הנוכחית
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        
        filterChain.doFilter(request, response);
    }
}