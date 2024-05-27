package com.example.alumni.feature.share;

import com.example.alumni.feature.share.*;
import com.example.alumni.feature.share.dto.*;
import com.example.alumni.domain.*;
import com.example.alumni.feature.social.SocialRepository;
import com.example.alumni.feature.social.dto.PostResponse;
import com.example.alumni.feature.social.dto.Thumbnail;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.ShareMapper;
import com.example.alumni.utils.CustomPageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ShareServiceImplTest {

    @InjectMocks
    private ShareServiceImpl shareService;

    @Mock
    private ShareRepository shareRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SocialRepository socialRepository;

    @Mock
    private ShareMapper shareMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllShares() {
        // Prepare data
        Share share = new Share();
        List<Thumbnail> thumbnails = List.of(); // Replace with actual List of Thumbnail instances
        LocalDate date = LocalDate.now(); // Replace with actual LocalDate instance
        PostResponse postResponse = new PostResponse("arg1", "arg2", thumbnails, date, 1, 2, "arg7");
        ShareResponse shareResponse = new ShareResponse("arg1", "arg2", postResponse, 1, 2);
        Page<Share> sharePage = new PageImpl<>(Collections.singletonList(share), PageRequest.of(0, 10), 1);

        // Define the behavior of mocked objects
        when(shareRepository.findAll(any(PageRequest.class))).thenReturn(sharePage);
        when(shareMapper.entityToResponse(any())).thenReturn(shareResponse);

        // Call the method to be tested
        CustomPageUtils<ShareResponse> result = shareService.getAllShares(0, 10, "http://localhost:8080/api/v1/shares");

        // Verify the result
        assertEquals(1, result.getTotal());
        assertEquals(shareResponse, result.getResults().get(0));
    }

    @Test
    public void testCreateShare() {
        // Prepare data
        ShareRequest shareRequest = new ShareRequest("arg1", "arg2", "arg3");
        Share share = new Share();
        User user = new User();
        Social social = new Social();
        List<Thumbnail> thumbnails = List.of(); // Replace with actual List of Thumbnail instances
        LocalDate date = LocalDate.now(); // Replace with actual LocalDate instance
        PostResponse postResponse = new PostResponse("arg1", "arg2", thumbnails, date, 1, 2, "arg7");
        ShareResponse shareResponse = new ShareResponse("arg1", "arg2", postResponse, 1, 2); // Replace with actual ShareResponse instance

        // Define the behavior of mocked objects
        when(shareMapper.requestToEntity(any())).thenReturn(share);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(socialRepository.findById(any())).thenReturn(Optional.of(social));
        when(shareRepository.save(any())).thenReturn(share);
        when(shareMapper.entityToResponse(any())).thenReturn(shareResponse);

        // Call the method to be tested
        ShareResponse result = shareService.createShare(shareRequest);

        // Verify the result
        assertEquals(shareResponse, result);
    }

    @Test
    public void testUpdateShare() {
        // Prepare data
        String id = "testId";
        ShareUpdateRequest shareUpdateRequest = new ShareUpdateRequest("arg1");
        Share share = new Share();
        List<Thumbnail> thumbnails = List.of(); // Replace with actual List of Thumbnail instances
        LocalDate date = LocalDate.now(); // Replace with actual LocalDate instance
        PostResponse postResponse = new PostResponse("arg1", "arg2", thumbnails, date, 1, 2, "arg7");
        ShareResponse shareResponse = new ShareResponse("arg1", "arg2", postResponse, 1, 2);

        // Define the behavior of mocked objects
        when(shareRepository.findById(any())).thenReturn(Optional.of(share));
        when(shareRepository.save(any())).thenReturn(share);
        when(shareMapper.entityToResponse(any())).thenReturn(shareResponse);

        // Call the method to be tested
        ShareResponse result = shareService.updateShare(id, shareUpdateRequest);

        // Verify the result
        assertEquals(shareResponse, result);
    }

    @Test
    public void testDeleteShare() {
        // Define the id
        String id = "testId";

        // Define the behavior of mocked objects
        doNothing().when(shareRepository).deleteById(any());

        // Call the method to be tested and verify that no exception is thrown
        assertDoesNotThrow(() -> shareService.deleteShare(id));
    }
}