package com.example.alumni.feature.admin;

import com.example.alumni.feature.user.UserDto.CreateUserRequest;
import com.example.alumni.feature.user.UserDto.UserResponse;
import com.example.alumni.feature.user.UserService;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<CustomPageUtils<UserResponse>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/admin";
        CustomPageUtils<UserResponse> userResponseCustomPage = userService.getAllUsers(page, size, baseUrl);
        return ResponseEntity.ok(userResponseCustomPage);
    }

    @PostMapping
    @Operation(summary = "Create user")
    public BaseResponse<UserResponse> createUser(@RequestBody CreateUserRequest userRequest) {
        return BaseResponse.<UserResponse>createSuccess()
                .setPayload(userService.createUsers(userRequest));
    }

    //update is verify
    @PutMapping("/{id}/verify")
    @Operation(summary = "Update user is verify")
    public BaseResponse<UserResponse> updateIsVerify(@PathVariable String id) {
        return BaseResponse.<UserResponse>updateSuccess()
                .setPayload(userService.isVerified(id));
    }

    //update is disable
    @PutMapping("/{id}/disable")
    @Operation(summary = "Update user is disable")
    public BaseResponse<UserResponse> updateIsDisable(@PathVariable String id) {
        return BaseResponse.<UserResponse>updateSuccess()
                .setPayload(userService.isDisabled(id));
    }

    //update is enable
    @PutMapping("/{id}/enable")
    @Operation(summary = "Update user is enable")
    public BaseResponse<UserResponse> updateIsEnable(@PathVariable String id) {
        return BaseResponse.<UserResponse>updateSuccess()
                .setPayload(userService.isEnable(id));
    }


    @GetMapping("/verify")
    @Operation(summary = "Get all users by is verify")
    public BaseResponse<List<UserResponse>> getAllUsersByIsVerify(){
        return BaseResponse.<List<UserResponse>>ok()
                .setPayload(userService.getAllUsersByIsVerify());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public BaseResponse<Void> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return BaseResponse.<Void>deleteSuccess();
    }




}
