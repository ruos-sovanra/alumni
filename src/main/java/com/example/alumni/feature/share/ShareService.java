package com.example.alumni.feature.share;


import com.example.alumni.feature.share.dto.LikeShareUpdateRequest;
import com.example.alumni.feature.share.dto.ShareRequest;
import com.example.alumni.feature.share.dto.ShareResponse;
import com.example.alumni.feature.share.dto.ShareUpdateRequest;
import com.example.alumni.utils.CustomPageUtils;

public interface ShareService {
    CustomPageUtils<ShareResponse> getAllShares(int page, int size, String baseUrl);
    ShareResponse getShareById(String id);
    ShareResponse createShare(ShareRequest share);
    ShareResponse updateShare(String id, ShareUpdateRequest share);
    void deleteShare(String id);
    ShareResponse updateLikes(String id, LikeShareUpdateRequest likeShareUpdateRequest);
}
