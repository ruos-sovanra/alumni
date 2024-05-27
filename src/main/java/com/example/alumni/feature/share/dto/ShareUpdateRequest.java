package com.example.alumni.feature.share.dto;

import lombok.Builder;

@Builder
public record ShareUpdateRequest(
        String caption
) {
}
