package com.example.alumni.feature.comment.dto;

import lombok.Builder;

@Builder
public record RepliedRequest(
        String comment,
        String userId,
        String socialId,
        String parentCommentId
) {
}
