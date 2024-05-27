package com.example.alumni.feature.auth.authDto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken,
        String userId
) {
}
