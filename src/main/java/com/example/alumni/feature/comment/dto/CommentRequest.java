package com.example.alumni.feature.comment.dto;

import lombok.Builder;

@Builder

public record CommentRequest(
        String comment,
        String userId,
        String socialId
) {
}
