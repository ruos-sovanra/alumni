package com.example.alumni.feature.auth;

import com.example.alumni.feature.auth.authDto.AuthResponse;
import com.example.alumni.feature.auth.authDto.LoginRequest;
import com.example.alumni.feature.auth.authDto.RefreshTokenRequest;
import com.example.alumni.security.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Mock
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Mock
    private TokenGenerator tokenGenerator;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void login() {
        // Create a mock Authentication object
        Authentication authentication = mock(Authentication.class);

        // Create a new LoginRequest object
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");

        // Define the behavior for daoAuthenticationProvider
        when(daoAuthenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Create a new AuthResponse object
        AuthResponse authResponse = new AuthResponse(
                "accessToken",
                "refreshToken",
                "id "
        );

        // Define the behavior for tokenGenerator
        when(tokenGenerator.generateTokens(authentication)).thenReturn(authResponse);

        // Call the login method
        AuthResponse result = authService.login(loginRequest);

        // Verify the result
        assertEquals(authResponse, result);

        // Verify that daoAuthenticationProvider.authenticate() was called
        verify(daoAuthenticationProvider, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Verify that tokenGenerator.generateTokens() was called
        verify(tokenGenerator, times(1)).generateTokens(authentication);
    }

    // Similar tests for refresh() and logout() methods
}