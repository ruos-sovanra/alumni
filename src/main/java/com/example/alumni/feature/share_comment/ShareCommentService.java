package com.example.alumni.feature.share_comment;


import com.example.alumni.feature.share_comment.dto.ShareCommentRequest;
import com.example.alumni.feature.share_comment.dto.ShareCommentResponse;
import com.example.alumni.feature.share_comment.dto.ShareRepliedRequest;
import com.example.alumni.utils.CustomPageUtils;

public interface ShareCommentService {

    ShareCommentResponse createShareComment(ShareCommentRequest shareCommentRequest);
    ShareCommentResponse createRepliedShareComment(ShareRepliedRequest shareRepliedRequest);
    ShareCommentResponse getShareComment(String shareCommentId);
    ShareCommentResponse updateShareComment(String shareCommentId, ShareCommentRequest shareCommentRequest);
    void deleteShareComment(String shareCommentId);
    CustomPageUtils<ShareCommentResponse> getAllShareComments(int page, int size, String baseUrl);

}
