package com.example.alumni.feature.user.UserDto;

import lombok.Builder;

@Builder
public record UserUpdateRequest(
        String firstName,
        String lastName,
        String username,
        String phone,
        String avatar,
        String coverUrl
) {
}
