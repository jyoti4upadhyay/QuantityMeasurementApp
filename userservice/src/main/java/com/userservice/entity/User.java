package com.userservice.entity;

import jakarta.persistence.*;
import com.userservice.entity.type.AuthProviderType;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = true)
    private String password;

    private String role;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    public User() {
    }

    public User(Long id, String username, String password, String role, String providerId, AuthProviderType providerType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.providerId = providerId;
        this.providerType = providerType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public AuthProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(AuthProviderType providerType) {
        this.providerType = providerType;
    }
}
