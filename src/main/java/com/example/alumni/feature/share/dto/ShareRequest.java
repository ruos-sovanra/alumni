package com.example.alumni.feature.share.dto;

import lombok.Builder;

@Builder
public record ShareRequest(
        String userId,
        String socialId,
        String caption

) {
}
