package com.example.alumni.feature.comment;


import com.example.alumni.feature.comment.dto.CommentRequest;
import com.example.alumni.feature.comment.dto.CommentResponse;
import com.example.alumni.feature.comment.dto.RepliedRequest;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Get all comments")
    public ResponseEntity<CustomPageUtils<CommentResponse>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/comments";
        CustomPageUtils<CommentResponse> commentResponseCustomPage = commentService.getComments(page, size, baseUrl);
        return ResponseEntity.ok(commentResponseCustomPage);
    }

    @GetMapping("/social/{socialId}")
    @Operation(summary = "Get comments by social id")
    public BaseResponse<List<CommentResponse>> getCommentsBySocialId(String socialId) {
        return BaseResponse.<List<CommentResponse>>ok()
                .setPayload(commentService.getCommentsBySocialId(socialId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get comments by user id")
    public BaseResponse<List<CommentResponse>> getCommentsByUserId(String userId) {
        return BaseResponse.<List<CommentResponse>>ok()
                .setPayload(commentService.getCommentsByUserId(userId));
    }

    @GetMapping("/parent/{parentCommentId}")
    @Operation(summary = "Get comments by parent comment id")
    public BaseResponse<List<CommentResponse>> getCommentsByParentComment(String parentCommentId) {
        return BaseResponse.<List<CommentResponse>>ok()
                .setPayload(commentService.getCommentsByParentComment(parentCommentId));
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Get comment by id")
    public BaseResponse<CommentResponse> getComment(String commentId) {
        return BaseResponse.<CommentResponse>ok()
                .setPayload(commentService.getComment(commentId));
    }


    @PostMapping
    @Operation(summary = "Create comment")
    public BaseResponse<CommentResponse> createComment(@RequestBody CommentRequest commentRequest) {
        return BaseResponse.<CommentResponse>updateSuccess()
                .setPayload(commentService.createComment(commentRequest));
    }

    @PostMapping("/replied")
    @Operation(summary = "Create replied comment")
    public BaseResponse<CommentResponse> createRepliedComment(@RequestBody RepliedRequest commentRequest) {
        return BaseResponse.<CommentResponse>createSuccess()
                .setPayload(commentService.createRepliedComment(commentRequest));
    }


    @PutMapping("/{commentId}")
    @Operation(summary = "Update comment")
    public BaseResponse<CommentResponse> updateComment(@PathVariable String commentId, @RequestBody CommentRequest commentRequest) {
        return BaseResponse.<CommentResponse>updateSuccess()
                .setPayload(commentService.updateComment(commentId, commentRequest));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete comment")
    public BaseResponse<Void> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
        return BaseResponse.<Void>deleteSuccess();
    }


}
