package com.userservice.service;

import com.userservice.dto.*;
import com.userservice.entity.User;
import com.userservice.entity.type.AuthProviderType;
import com.userservice.repository.UserRepository;
import com.userservice.security.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       AuthUtil authUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
    }

    public SignupResponseDto signUp(SignupRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole(),
                "User registered successfully"
        );
    }


    public LoginResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword() == null ? "" : user.getPassword())
                .authorities(user.getRole())
                .build();

        String token = authUtil.generateToken(userDetails);

        return new LoginResponseDto(
                token,
                request.getUsername(),
                "Login successful"
        );
    }

    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);
        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(null);
                    newUser.setRole("ROLE_USER");
                    newUser.setProviderId(providerId);
                    newUser.setProviderType(providerType);
                    return userRepository.save(newUser);
                });

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password("")
                .authorities(user.getRole())
                .build();

        String token = authUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDto(token, user.getUsername(), "OAuth2 login successful"));
    }
}
