package com.userservice.controller;

import com.userservice.dto.*;
import com.userservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public SignupResponseDto signUp(
            @Valid @RequestBody SignupRequestDto request) {
        return authService.signUp(request);
    }

    @PostMapping("/login")
    public LoginResponseDto login(
            @Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }
}
