package com.example.alumni.feature.share_comment.dto;

import lombok.Builder;

@Builder
public record ShareRepliedRequest(
        String userId,
        String shareId,
        String comment,
        String parentCommentId
){
}
