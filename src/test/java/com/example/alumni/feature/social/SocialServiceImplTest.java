package com.example.alumni.feature.social;

import com.example.alumni.domain.Post;
import com.example.alumni.domain.Social;

import com.example.alumni.domain.User;
import com.example.alumni.feature.repo.PostRepository;
import com.example.alumni.feature.repo.ThumbnailRepository;
import com.example.alumni.feature.social.dto.PostRequest;
import com.example.alumni.feature.social.dto.PostResponse;
import com.example.alumni.feature.social.dto.Thumbnail;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.SocialMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SocialServiceImplTest {

    @Mock
    private SocialRepository socialRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ThumbnailRepository thumbnailRepository;

    @Mock
    private SocialMapper socialMapper;

    @InjectMocks
    private SocialServiceImpl socialService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost() {
        String userId = "userId";
        String caption = "caption";
        List<Thumbnail> thumbnails = new ArrayList<>(
                Collections.singletonList(new Thumbnail( "url"))
        );
        LocalDate date = LocalDate.now();
        Integer likes = 0;
        Integer shares = 0;
        String postId = "postId";

        PostRequest postRequest = new PostRequest(userId, caption, thumbnails);
        Social social = new Social();
        Post post = new Post();
        User user = new User();

        // add Thumbnail objects to the list
        PostResponse postResponse = new PostResponse(userId, caption, thumbnails, date, likes, shares, postId);

        when(socialMapper.toSocial(any(PostRequest.class))).thenReturn(social);
        when(postRepository.findByType(anyString())).thenReturn(post);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(socialRepository.save(any(Social.class))).thenReturn(social);
        when(socialMapper.toPostResponse(any(Social.class))).thenReturn(postResponse);

        PostResponse result = socialService.createPost(postRequest);

        assertEquals(postResponse, result);
        verify(socialMapper, times(1)).toSocial(any(PostRequest.class));
        verify(postRepository, times(1)).findByType(anyString());
        verify(userRepository, times(1)).findById(anyString());
        verify(socialRepository, times(1)).save(any(Social.class));
        verify(socialMapper, times(1)).toPostResponse(any(Social.class));
    }


    @Test
    public void testUpdatePost() {
        String userId = "userId";
        String caption = "caption";
        List<Thumbnail> thumbnails = new ArrayList<>();
        // add Thumbnail objects to the list
        PostRequest postRequest = new PostRequest(userId, caption, thumbnails);
        Social social = new Social();
        Post post = new Post();
        User user = new User();
        PostResponse postResponse = new PostResponse(userId, caption, thumbnails, LocalDate.now(), 0, 0, "SOCIAL");

        when(socialRepository.findById(anyString())).thenReturn(Optional.of(social));
        when(postRepository.findByType(anyString())).thenReturn(post);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(socialRepository.save(any(Social.class))).thenReturn(social);
        when(socialMapper.toPostResponse(any(Social.class))).thenReturn(postResponse);

        PostResponse result = socialService.updatePost("postId", postRequest);

        assertEquals(postResponse, result);
        verify(socialRepository, times(1)).findById(anyString());
        verify(postRepository, times(1)).findByType(anyString());
        verify(userRepository, times(1)).findById(anyString());
        verify(socialRepository, times(1)).save(any(Social.class));
        verify(socialMapper, times(1)).toPostResponse(any(Social.class));
    }

    @Test
    public void testDeletePost() {
        doNothing().when(socialRepository).deleteById(anyString());

        socialService.deletePost("postId");

        verify(socialRepository, times(1)).deleteById(anyString());
    }
}