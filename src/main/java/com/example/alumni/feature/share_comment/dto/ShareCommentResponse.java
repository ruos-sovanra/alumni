package com.example.alumni.feature.share_comment.dto;


import com.example.alumni.domain.ShareComment;
import lombok.Builder;

import java.util.List;

@Builder
public record ShareCommentResponse(
        String id,
        String userName,
        String comment,
        List<ShareCommentResponse> replies,
        String parentCommentId, // existing line
        boolean replied // Add this line
) {
    public static ShareCommentResponse withNestedComment(ShareComment comment, ShareCommentResponse nestedComment, List<ShareCommentResponse> replies) {
        return new ShareCommentResponse(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getComment(),
                replies,
                comment.getParentComment() != null ? comment.getParentComment().getId() : null, // existing line
                nestedComment != null // Add this line
        );
    }
}
