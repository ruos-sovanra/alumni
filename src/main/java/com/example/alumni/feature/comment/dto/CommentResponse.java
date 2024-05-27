package com.example.alumni.feature.comment.dto;


import com.example.alumni.domain.Comment;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentResponse(
        String id,
        String userName,
        String comment,
        List<CommentResponse> replies,
        String parentCommentId, // existing line
        boolean replied // Add this line
) {
    public static CommentResponse withNestedComment(Comment comment, List<CommentResponse> replies) {
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getComment(),
                replies,
                comment.getParentComment() != null ? comment.getParentComment().getId() : null, // existing line
                comment.getParentComment() != null // Add this line
        );
    }
}