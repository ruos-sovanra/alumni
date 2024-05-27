package com.example.alumni.feature.user;

import com.example.alumni.domain.AccountType;
import com.example.alumni.domain.Role;
import com.example.alumni.domain.User;
import com.example.alumni.feature.repo.AccountTypeRepository;
import com.example.alumni.feature.repo.RoleRepository;
import com.example.alumni.mapper.UserMapper;
import com.example.alumni.feature.user.UserDto.CreateUserRequest;
import com.example.alumni.feature.user.UserDto.RegisterRequest;
import com.example.alumni.feature.user.UserDto.UserResponse;
import com.example.alumni.feature.user.UserDto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user = new User();
        UserResponse userResponse = new UserResponse(
                "id",
                "username",
                "firstName",
                "lastName",
                "email",
                "phone",
                true, // isVerified
                true, // isDisabled
                "avatar",
                "coverUrl",
                "roleName",
                "accTypeName"
        );
        Page<User> userPage = new PageImpl<>(Arrays.asList(user));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(userPage);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        var result = userService.getAllUsers(0, 1, "http://localhost");

        assertEquals(1, result.getResults().size());
        assertEquals(userResponse, result.getResults().get(0));
    }

    @Test
    public void testCreateUsers() {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "username",
                "password",
                "confirmPassword",
                "firstName",
                "lastName",
                "email",
                "phone",
                "avatar",
                "coverUrl",
                "roleName"
        );
        User user = new User();
        UserResponse userResponse = new UserResponse(
                "id",
                "username",
                "firstName",
                "lastName",
                "email",
                "phone",
                true, // isVerified
                true, // isDisabled
                "avatar",
                "coverUrl",
                "roleName",
                "accTypeName"
        );
        when(userMapper.createToUserResponse(createUserRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.createUsers(createUserRequest);

        assertEquals(userResponse, result);
    }

    @Test
    public void testUpdateUser() {
        String id = "testId";
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
                "avatar",
                "firstName",
                "lastName",
                "phone",
                "username",
                "coverUrl"
        );
        User user = new User();
        UserResponse userResponse = new UserResponse(
                "id",
                "username",
                "firstName",
                "lastName",
                "email",
                "phone",
                true, // isVerified
                true, // isDisabled
                "avatar",
                "coverUrl",
                "roleName",
                "accTypeName"
        );
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.updateUser(id, userUpdateRequest);

        assertEquals(userResponse, result);
    }

    @Test
    public void testDeleteUser() {
        String id = "testId";
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(id);

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    // Add more test methods for other methods in UserServiceImpl
}