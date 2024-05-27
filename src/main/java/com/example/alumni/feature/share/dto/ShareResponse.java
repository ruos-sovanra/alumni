package com.example.alumni.feature.share.dto;


import com.example.alumni.feature.social.dto.PostResponse;
import lombok.Builder;

@Builder
public record ShareResponse(
        String id,
        String caption,
        PostResponse post,
        Integer likes,
        Integer shares
) {
}
