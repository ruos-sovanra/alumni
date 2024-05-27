package com.example.alumni.feature.share;


import com.example.alumni.feature.share.dto.LikeShareUpdateRequest;
import com.example.alumni.feature.share.dto.ShareRequest;
import com.example.alumni.feature.share.dto.ShareResponse;
import com.example.alumni.feature.share.dto.ShareUpdateRequest;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareRestController {

    private final ShareService shareService;

    @GetMapping
    @Operation(summary = "Get all shares")
    public ResponseEntity<CustomPageUtils<ShareResponse>> getAllShares(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/shares";
        CustomPageUtils<ShareResponse> shareResponseCustomPage = shareService.getAllShares(page, size, baseUrl);
        return ResponseEntity.ok(shareResponseCustomPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get share by id")
    public BaseResponse<ShareResponse> getShareById(String id) {
        return BaseResponse.<ShareResponse>ok()
                .setPayload(shareService.getShareById(id));
    }

    @PostMapping
    @Operation(summary = "Create share")
    public BaseResponse<ShareResponse> createShare(@RequestBody ShareRequest share) {
        return BaseResponse.<ShareResponse>createSuccess()
                .setPayload(shareService.createShare(share));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update share")
    public BaseResponse<ShareResponse> updateShare(@PathVariable String id, @RequestBody ShareUpdateRequest share) {
        return BaseResponse.<ShareResponse>updateSuccess()
                .setPayload(shareService.updateShare(id, share));
    }
    @PatchMapping("/{id}/likes")
    @Operation(summary = "Update likes of a share")
    public BaseResponse<ShareResponse> updateLikes(@PathVariable String id, @RequestBody LikeShareUpdateRequest likeShareUpdateRequest) {
        return BaseResponse.<ShareResponse>updateSuccess()
                .setPayload(shareService.updateLikes(id, likeShareUpdateRequest));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete share")
    public BaseResponse<Void> deleteShare(@PathVariable String id) {
        shareService.deleteShare(id);
        return BaseResponse.<Void>deleteSuccess();
    }

    
}
