package com.quantitymeasurementservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurementservice.dto.ConvertRequest;
import com.quantitymeasurementservice.dto.QuantityRequest;
import com.quantitymeasurementservice.repository.QuantityMeasurementRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuantityMeasurementControllerIntegrationTest {

    private static final String SECRET = "6f3c9c2a9b1d4e8f7a0c5d9e2b1c4f8a7d6e9b0c3a5f7d1e8b9c2a4d6f8e1a3";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuantityMeasurementRepository repository;

    private String authHeader;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        String token = Jwts.builder()
                .setSubject("john@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();

        authHeader = "Bearer " + token;
    }

    @Test
    void allProtectedQuantityOperationsShouldWorkAndBeStoredInHistory() throws Exception {
        QuantityRequest arithmeticRequest = new QuantityRequest();
        arithmeticRequest.setValue1(1.0);
        arithmeticRequest.setUnit1("FEET");
        arithmeticRequest.setValue2(12.0);
        arithmeticRequest.setUnit2("INCHES");

        mockMvc.perform(post("/api/quantity/add")
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(arithmeticRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("ADDITION"))
                .andExpect(jsonPath("$.resultValue").value(2.0))
                .andExpect(jsonPath("$.resultUnit").value("FEET"));

        mockMvc.perform(post("/api/quantity/subtract")
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(arithmeticRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("SUBTRACTION"))
                .andExpect(jsonPath("$.resultValue").value(0.0));

        mockMvc.perform(post("/api/quantity/divide")
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(arithmeticRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("DIVISION"))
                .andExpect(jsonPath("$.resultUnit").value("RATIO"));

        mockMvc.perform(post("/api/quantity/compare")
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(arithmeticRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("COMPARISON"))
                .andExpect(jsonPath("$.resultValue").value(1.0));

        ConvertRequest convertRequest = new ConvertRequest();
        convertRequest.setValue(1.0);
        convertRequest.setFromUnit("GALLON");
        convertRequest.setToUnit("LITRE");

        mockMvc.perform(post("/api/quantity/convert")
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(convertRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("CONVERT"))
                .andExpect(jsonPath("$.resultUnit").value("LITRE"));

        mockMvc.perform(get("/api/quantity/history")
                        .header(HttpHeaders.AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("CONVERT"))
                .andExpect(jsonPath("$[1].operation").value("COMPARISON"))
                .andExpect(jsonPath("$[2].operation").value("DIVISION"))
                .andExpect(jsonPath("$[3].operation").value("SUBTRACTION"))
                .andExpect(jsonPath("$[4].operation").value("ADDITION"));
    }

    @Test
    void quantityEndpointsShouldRejectMissingToken() throws Exception {
        mockMvc.perform(get("/api/quantity/history"))
                .andExpect(status().isUnauthorized());
    }
}
