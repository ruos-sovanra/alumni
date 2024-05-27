package com.example.alumni.feature.user;

import com.example.alumni.feature.user.UserDto.CreateUserRequest;
import com.example.alumni.feature.user.UserDto.RegisterRequest;
import com.example.alumni.feature.user.UserDto.UserResponse;
import com.example.alumni.feature.user.UserDto.UserUpdateRequest;
import com.example.alumni.utils.CustomPageUtils;

import java.util.List;

public interface UserService {

    CustomPageUtils<UserResponse> getAllUsers(int page, int size, String baseUrl);
    UserResponse registerAccount(RegisterRequest registerRequest);
    UserResponse createUsers(CreateUserRequest createUserRequest);
    UserResponse updateUser(String id, UserUpdateRequest userUpdateRequest);
    void deleteUser(String id);
    UserResponse isVerified(String id);
    UserResponse isDisabled(String id);
    UserResponse isEnable(String id);
    List<UserResponse> getAllUsersByIsVerify();
}
