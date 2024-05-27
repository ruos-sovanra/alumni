package com.example.alumni.feature.social.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PostRequest(
        String userId,
        String caption,
        List<Thumbnail> thumbnails
) {
}
