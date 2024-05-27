package com.example.alumni.feature.auth.authDto;

import lombok.Builder;

@Builder
public record LoginRequest(
        String email,
        String password
) {
}
