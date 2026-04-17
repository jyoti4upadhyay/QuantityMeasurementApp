package com.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.dto.LoginRequestDto;
import com.userservice.dto.SignupRequestDto;
import com.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private SignupRequestDto signupRequest;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        signupRequest = new SignupRequestDto();
        signupRequest.setUsername("john@example.com");
        signupRequest.setPassword("secret123");
        signupRequest.setRole("ROLE_USER");
    }

    @Test
    void signupAndLoginShouldReturnJwtToken() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));

        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setUsername("john@example.com");
        loginRequest.setPassword("secret123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john@example.com"))
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void duplicateSignupShouldFail() throws Exception {
        String payload = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk());

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }
}
