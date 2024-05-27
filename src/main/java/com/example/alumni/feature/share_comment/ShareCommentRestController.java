package com.example.alumni.feature.share_comment;


import com.example.alumni.feature.share_comment.dto.ShareCommentRequest;
import com.example.alumni.feature.share_comment.dto.ShareCommentResponse;
import com.example.alumni.feature.share_comment.dto.ShareRepliedRequest;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/share-comments")
@RequiredArgsConstructor
public class ShareCommentRestController {

    private final ShareCommentService shareCommentService;

    @GetMapping
    @Operation(summary = "Get all share comments")
    public ResponseEntity<CustomPageUtils<ShareCommentResponse>> getAllShareComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/share-comments";
        CustomPageUtils<ShareCommentResponse> shareCommentResponseCustomPage = shareCommentService.getAllShareComments(page, size, baseUrl);
        return ResponseEntity.ok(shareCommentResponseCustomPage);
    }

    @PostMapping
    @Operation(summary = "Create share comment")
    public BaseResponse<ShareCommentResponse> createShareComment(@RequestBody ShareCommentRequest shareCommentRequest) {
        return BaseResponse.<ShareCommentResponse>createSuccess()
                .setPayload(shareCommentService.createShareComment(shareCommentRequest));
    }

    @PostMapping("/replied")
    @Operation(summary = "Create replied share comment")
    public BaseResponse<ShareCommentResponse> createRepliedShareComment(@RequestBody ShareRepliedRequest shareCommentRequest) {
        return BaseResponse.<ShareCommentResponse>createSuccess()
                .setPayload(shareCommentService.createRepliedShareComment(shareCommentRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get share comment by id")
    public BaseResponse<ShareCommentResponse> getShareCommentById(String id) {
        return BaseResponse.<ShareCommentResponse>ok()
                .setPayload(shareCommentService.getShareComment(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update share comment")
    public BaseResponse<ShareCommentResponse> updateShareComment(@PathVariable String id, @RequestBody ShareCommentRequest shareCommentRequest) {
        return BaseResponse.<ShareCommentResponse>updateSuccess()
                .setPayload(shareCommentService.updateShareComment(id, shareCommentRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete share comment")
    public BaseResponse<Void> deleteShareComment(@PathVariable String id) {
        shareCommentService.deleteShareComment(id);
        return BaseResponse.<Void>deleteSuccess();
    }
}
