package com.example.alumni.feature.social;


import com.example.alumni.domain.Post;
import com.example.alumni.domain.Social;
import com.example.alumni.domain.Thumbnail;
import com.example.alumni.domain.User;
import com.example.alumni.feature.repo.PostRepository;
import com.example.alumni.feature.repo.ThumbnailRepository;
import com.example.alumni.feature.social.dto.LikeUpdateRequest;
import com.example.alumni.feature.social.dto.PostRequest;
import com.example.alumni.feature.social.dto.PostResponse;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.SocialMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialServiceImpl implements SocialService {

    private final SocialRepository socialRepository;
    private final PostRepository postTypeRepository;
    private final UserRepository userRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final SocialMapper socialMapper;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Social social = socialMapper.toSocial(postRequest);
        Post postType = postTypeRepository.findByType("SOCIAL");
        User user = userRepository.findById(postRequest.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        social.setUser(user);
        social.setLikes(0);
        social.setShares(0);
        social.setPostType(postType);
        socialRepository.save(social);

        List<Thumbnail> thumbnails = social.getThumbnails();

        if (thumbnails != null) {
            for (Thumbnail thumbnail : thumbnails) {
                thumbnail.setSocial(social);
                thumbnailRepository.save(thumbnail);
            }
        }

        return socialMapper.toPostResponse(social);
    }

    @Override
    public CustomPageUtils<PostResponse> getPosts(int page, int size, String baseUrl) {
        Page<Social> socials = socialRepository.findAll(Pageable.ofSize(size).withPage(page));
        return CustomPagination(socials.map(socialMapper::toPostResponse), baseUrl);
    }


    @Override
    public PostResponse updatePost(String postId, PostRequest postRequest) {
        Social social = socialRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Post postType = postTypeRepository.findByType("SOCIAL");

        User user = userRepository.findById(postRequest.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        social.setUser(user);
        social.setPostType(postType);
        social.setCaption(postRequest.caption());
        socialRepository.save(social);
        return socialMapper.toPostResponse(social);
    }

    @Override
    public void deletePost(String postId) {
        socialRepository.deleteById(postId);
    }

    @Override
    public PostResponse updateLikes(String id, LikeUpdateRequest likeUpdateRequest) {
        Social social = socialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        social.setLikes(social.getLikes() + likeUpdateRequest.likes());
        socialRepository.save(social);
        return socialMapper.toPostResponse(social);
    }

    public CustomPageUtils<PostResponse> CustomPagination(Page<PostResponse> page, String baseUrl){
        CustomPageUtils<PostResponse> customPage = new CustomPageUtils<>();
        if(page.hasNext()){
            customPage.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }
        if (page.hasPrevious()){
            customPage.setPrevious(baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize());
        }
        customPage.setTotal((int) page.getTotalElements());
        customPage.setResults(page.getContent());
        return customPage;
    }
}
