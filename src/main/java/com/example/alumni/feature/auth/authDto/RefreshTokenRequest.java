package com.example.alumni.feature.auth.authDto;

import lombok.Builder;

@Builder
public record RefreshTokenRequest(String refreshToken) {
}
