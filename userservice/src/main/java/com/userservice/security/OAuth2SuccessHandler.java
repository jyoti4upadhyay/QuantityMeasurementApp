package com.userservice.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.userservice.dto.LoginResponseDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.userservice.service.AuthService;
import org.springframework.context.annotation.Lazy;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    @Lazy

    private AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
                                        
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        System.out.println("Success handler hit");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("OAuth SUCCESS HANDLER HIT");

        String registrationId = token.getAuthorizedClientRegistrationId();

        LoginResponseDto loginResponse =
                authService.handleOAuth2LoginRequest(oAuth2User, registrationId).getBody();

        String jwt = loginResponse.getToken();

        // String redirectUrl = "http://localhost:5501/index.html?token=" + jwt;
        String redirectUrl = "http://localhost:4200/login?token=" + jwt;

        response.sendRedirect(redirectUrl);
    }
}
