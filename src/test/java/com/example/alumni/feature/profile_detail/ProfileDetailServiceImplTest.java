package com.example.alumni.feature.profile_detail;

import com.example.alumni.domain.*;
import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import com.example.alumni.feature.user_detail.ProfileDetailServiceImpl;
import com.example.alumni.feature.user_detail.UserDetailRepository;
import com.example.alumni.feature.user_detail.dto.AdminUpdateRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailResponse;
import com.example.alumni.feature.employ.EmployRepository;
import com.example.alumni.feature.generation.GenerationRepository;
import com.example.alumni.feature.study_abroad.StudyAbroadRepository;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.UserDetailMapper;
import com.example.alumni.utils.CustomPageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileDetailServiceImplTest {

    @Mock
    private UserDetailRepository userDetailRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GenerationRepository generationRepository;

    @Mock
    private StudyAbroadRepository studyAbroadRepository;

    @Mock
    private EmployRepository employTypeRepository;

    @Mock
    private UserDetailMapper userDetailMapper;

    @InjectMocks
    private ProfileDetailServiceImpl profileDetailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUserDetails() {
        Map<String, Object> emptyMap = new HashMap<String, Object>();
        GenerationResponse generationResponse = new GenerationResponse(
                "id",
                "name",
                2
        );
        // Arrange
        int page = 1;
        int size = 10;
        String baseUrl = "http://localhost";
        Optional<String> genType = Optional.empty();
        Optional<String> numGen = Optional.empty();
        Optional<Boolean> isGraduated = Optional.empty();
        Optional<Boolean> isEmployed = Optional.empty();
        Optional<String> country = Optional.empty();
        Optional<String> employType = Optional.empty();

        Page<UserDetail> userDetailPage = mock(Page.class);
        when(userDetailRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userDetailPage);

        UserDetailResponse userDetailResponse = new UserDetailResponse(
                "id",
                "firstName",
                "lastName",
                "email",
                "telephone",
                "address",
                "nationality",
                "gender",
                emptyMap,
                emptyMap, // educations
                emptyMap, // workExperiences
                emptyMap, // skills
                emptyMap, // interests
                emptyMap, // languages
                generationResponse,
                "employType",
                "studyAbroadCountry",
                "imageUrl"
        );

        when(userDetailMapper.toUserDetailResponse(any(UserDetail.class))).thenReturn(userDetailResponse);

        // Act
        CustomPageUtils<UserDetailResponse> result = profileDetailService.getAllUserDetails(page, size, baseUrl, genType, numGen, isGraduated, isEmployed, country, employType);

        // Assert
        verify(userDetailRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(userDetailMapper, times((int) userDetailPage.getNumberOfElements())).toUserDetailResponse(any(UserDetail.class));
        assertNotNull(result);
    }

    @Test
    public void testCreateUserDetail() {

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        GenerationResponse generationResponse = new GenerationResponse(
                "id",
                "name",
                2
        );

        UserDetailRequest userDetailRequest = new UserDetailRequest(
                "userId",
                "firstName",
                "lastName",
                "email",
                "telephone",
                "address",
                "nationality",
                emptyMap,
                emptyMap, // educations
                emptyMap, // workExperiences
                emptyMap, // skills
                emptyMap, // interests
                emptyMap,
                // languages
                "gender"
        );

        User user = new User();
        // Set any required fields for the User object

        UserDetail userDetail = new UserDetail();


        UserDetailResponse userDetailResponse = new UserDetailResponse(
                "id",
                "firstName",
                "lastName",
                "email",
                "telephone",
                "address",
                "nationality",
                "gender",
                emptyMap,
                emptyMap, // educations
                emptyMap, // workExperiences
                emptyMap, // skills
                emptyMap, // interests
                emptyMap, // languages
                generationResponse,
                "employType",
                "studyAbroadCountry",
                "imageUrl"
        );

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userDetailMapper.toUserDetail(any(UserDetailRequest.class))).thenReturn(userDetail);
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(userDetail);
        when(userDetailMapper.toUserDetailResponse(any(UserDetail.class))).thenReturn(userDetailResponse);

        // Act
        UserDetailResponse result = profileDetailService.createUserDetail(userDetailRequest);

        // Assert
        verify(userRepository).findById(anyString());
        verify(userDetailMapper).toUserDetail(any(UserDetailRequest.class));
        verify(userDetailRepository).save(any(UserDetail.class));
        verify(userDetailMapper).toUserDetailResponse(any(UserDetail.class));
        assertNotNull(result);
    }

    @Test
    public void testUpdateUserDetail() {
        // Arrange
        String id = "id";
        Map<String, Object> emptyMap = new HashMap<String, Object>();
        GenerationResponse generationResponse = new GenerationResponse(
                "id",
                "name",
                2
        );

        UserDetailRequest userDetailRequest = new UserDetailRequest(
                "userId",
                "firstName",
                "lastName",
                "email",
                "telephone",
                "address",
                "nationality",
                emptyMap,
                emptyMap, // educations
                emptyMap, // workExperiences
                emptyMap, // skills
                emptyMap, // interests
                emptyMap, // languages
                "gender"
        );

        User user = new User();
        // Set any required fields for the User object

        UserDetail userDetail = new UserDetail();

        UserDetailResponse userDetailResponse = new UserDetailResponse(
                "id",
                "firstName",
                "lastName",
                "email",
                "telephone",
                "address",
                "nationality",
                "gender",
                emptyMap,
                emptyMap, // educations
                emptyMap, // workExperiences
                emptyMap, // skills
                emptyMap, // interests
                emptyMap, // languages
                generationResponse,
                "employType",
                "studyAbroadCountry",
                "imageUrl"
        );

        when(userDetailRepository.findById(anyString())).thenReturn(Optional.of(userDetail));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(userDetail);
        when(userDetailMapper.toUserDetailResponse(any(UserDetail.class))).thenReturn(userDetailResponse);

        // Act
        UserDetailResponse result = profileDetailService.updateUserDetail(id, userDetailRequest);

        // Assert
        verify(userDetailRepository).findById(anyString());
        verify(userRepository).findById(anyString());
        verify(userDetailRepository).save(any(UserDetail.class));
        verify(userDetailMapper).toUserDetailResponse(any(UserDetail.class));
        assertNotNull(result);
    }


    @Test
    public void testDeleteUserDetail() {
        doNothing().when(userDetailRepository).deleteById(anyString());
        profileDetailService.deleteUserDetail("id");
        verify(userDetailRepository).deleteById(anyString());
    }
}
