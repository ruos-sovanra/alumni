package com.example.alumni.feature.user.UserDto;

import lombok.Builder;

@Builder
public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phone,
        Boolean isDisabled,
        Boolean isVerified,
        String avatar,
        String coverUrl,
        String roleName,
        String AccTypeName
) {
}