package com.example.alumni.feature.user;

import com.example.alumni.feature.user.UserDto.UserResponse;
import com.example.alumni.feature.user.UserDto.UserUpdateRequest;
import com.example.alumni.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public BaseResponse<UserResponse> updateUser(@PathVariable String id, UserUpdateRequest userRequest) {
        return BaseResponse.<UserResponse>updateSuccess()
                .setPayload(userService.updateUser(id, userRequest));
    }
}
