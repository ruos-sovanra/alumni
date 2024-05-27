package com.example.alumni.feature.social.dto;

import lombok.Builder;

@Builder
public record LikeUpdateRequest(
        Integer likes
) {
}
