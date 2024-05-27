package com.example.alumni.feature.fogot_password.dto;

import lombok.Builder;

@Builder
public record ChangePasswordWithOldPassword(
        String oldPassword,
        String newPassword,
        String confirmPassword
) {
}
