package com.example.auth_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // 'user' is a reserved word in Postgres, so we use 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // This will be the Name/Email

    @Column(nullable = false)
    private String password; // This will be the Hashed Password

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Admin or Driver
}