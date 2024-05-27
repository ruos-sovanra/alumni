package com.example.alumni.feature.social;


import com.example.alumni.feature.social.dto.LikeUpdateRequest;
import com.example.alumni.feature.social.dto.PostRequest;
import com.example.alumni.feature.social.dto.PostResponse;
import com.example.alumni.utils.CustomPageUtils;

public interface SocialService {
    PostResponse createPost(PostRequest postRequest);
    CustomPageUtils<PostResponse> getPosts(int page, int size, String baseUrl);
    PostResponse updatePost(String postId, PostRequest postRequest);
    void deletePost(String postId);
    PostResponse updateLikes(String id , LikeUpdateRequest likeUpdateRequest);
}
