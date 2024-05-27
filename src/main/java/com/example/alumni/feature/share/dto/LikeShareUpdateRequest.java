package com.example.alumni.feature.share.dto;

import lombok.Builder;

@Builder
public record LikeShareUpdateRequest(
        Integer likes
) {
}
