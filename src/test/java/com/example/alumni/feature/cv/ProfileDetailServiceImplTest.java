package com.example.alumni.feature.cv;

import com.example.alumni.domain.User;
import com.example.alumni.domain.UserDetail;
import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.feature.user_detail.ProfileDetailServiceImpl;
import com.example.alumni.feature.user_detail.UserDetailRepository;
import com.example.alumni.feature.user_detail.dto.UserDetailRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailResponse;
import com.example.alumni.mapper.UserDetailMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileDetailServiceImplTest {

    @Mock
    private UserDetailRepository userDetailRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailMapper userDetailMapper;

    @InjectMocks
    private ProfileDetailServiceImpl profileDetailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserDetail() {
        Map<String, Object> map = new HashMap<>();
        // populate the map with values

        UserDetailRequest userDetailRequest = new UserDetailRequest(
                "donationTypeId",
                "amount",
                "eventId",
                "thumbnail",
                "supportTypeId",
                "userId",
                "anotherString",
                map,
                map,
                map,
                map,
                map,
                map,
                "anotherString"
        );

        UserDetail userDetail = new UserDetail();
        User user = new User();

        GenerationResponse generationResponse = new GenerationResponse(
                "string1",
                "string2",
                123 // replace with actual integer value
        );
        // populate the generationResponse with values

        UserDetailResponse userDetailResponse = new UserDetailResponse(
                "string1",
                "string2",
                "string3",
                "string4",
                "string5",
                "string6",
                "string7",
                "string8",
                map,
                map,
                map,
                map,
                map,
                map,
                generationResponse,
                "string9",
                "string10",
                "string11"
        );

        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(user));
        when(userDetailMapper.toUserDetail(any(UserDetailRequest.class))).thenReturn(userDetail);
        when(userDetailMapper.toUserDetailResponse(any(UserDetail.class))).thenReturn(userDetailResponse);
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(userDetail);

        UserDetailResponse result = profileDetailService.createUserDetail(userDetailRequest);

        assertEquals(userDetailResponse, result);
        verify(userRepository, times(1)).findById(anyString());
        verify(userDetailMapper, times(1)).toUserDetail(any(UserDetailRequest.class));
        verify(userDetailMapper, times(1)).toUserDetailResponse(any(UserDetail.class));
        verify(userDetailRepository, times(1)).save(any(UserDetail.class));
    }

    @Test
    public void testCreateUserDetailUserNotFound() {
        Map<String, Object> map = new HashMap<>();
        // populate the map with values

        UserDetailRequest userDetailRequest = new UserDetailRequest(
                "donationTypeId",
                "amount",
                "eventId",
                "thumbnail",
                "supportTypeId",
                "userId",
                "anotherString",
                map,
                map,
                map,
                map,
                map,
                map,
                "anotherString"
        );

        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            profileDetailService.createUserDetail(userDetailRequest);
        });
    }
}