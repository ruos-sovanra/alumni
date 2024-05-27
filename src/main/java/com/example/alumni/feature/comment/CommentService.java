package com.example.alumni.feature.comment;


import com.example.alumni.feature.comment.dto.CommentRequest;
import com.example.alumni.feature.comment.dto.CommentResponse;
import com.example.alumni.feature.comment.dto.RepliedRequest;
import com.example.alumni.utils.CustomPageUtils;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CommentRequest commentRequest);
    CommentResponse createRepliedComment(RepliedRequest repliedRequest);
    CommentResponse getComment(String commentId);
    CommentResponse updateComment(String commentId, CommentRequest commentRequest);
    void deleteComment(String commentId);
    List<CommentResponse> getCommentsBySocialId(String socialId);
    List<CommentResponse> getCommentsByUserId(String userId);
    List<CommentResponse> getCommentsByParentComment(String parentCommentId);
    CustomPageUtils<CommentResponse> getComments(int page, int size, String baseUrl);
}
