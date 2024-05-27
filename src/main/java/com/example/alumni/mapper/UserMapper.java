package com.example.alumni.mapper;

import com.example.alumni.domain.User;
import com.example.alumni.feature.user.UserDto.CreateUserRequest;
import com.example.alumni.feature.user.UserDto.RegisterRequest;
import com.example.alumni.feature.user.UserDto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User requestToUserResponse(RegisterRequest userRequest);

    User createToUserResponse(CreateUserRequest userRequest);

}
