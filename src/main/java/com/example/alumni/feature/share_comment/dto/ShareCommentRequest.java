package com.example.alumni.feature.share_comment.dto;

import lombok.Builder;

@Builder
public record ShareCommentRequest(
           String userId,
        String shareId,
        String comment
) {
}
