package com.example.alumni.feature.auth;


import com.example.alumni.feature.auth.authDto.AuthResponse;
import com.example.alumni.feature.auth.authDto.LoginRequest;
import com.example.alumni.feature.auth.authDto.RefreshTokenRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
   AuthResponse refresh(RefreshTokenRequest request);
    void logout(String refreshToken);
}
