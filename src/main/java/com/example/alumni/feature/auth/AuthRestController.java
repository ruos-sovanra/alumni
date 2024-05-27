package com.example.alumni.feature.auth;


import com.example.alumni.feature.auth.authDto.AuthResponse;
import com.example.alumni.feature.auth.authDto.LoginRequest;
import com.example.alumni.feature.auth.authDto.RefreshTokenRequest;
import com.example.alumni.feature.user.UserDto.RegisterRequest;
import com.example.alumni.feature.user.UserService;
import com.example.alumni.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirements(value = {})
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register user")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest userRequest) {
        try {
            return ResponseEntity.ok(BaseResponse.createSuccess()
                    .setPayload(userService.registerAccount(userRequest)));
        } catch (ConstraintViolationException ex) {
            HashMap<String, Object> errors = new HashMap<>();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(BaseResponse.badRequest().setMetadata(errors));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public BaseResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.login(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token")
    public BaseResponse<AuthResponse> refreshToken( @RequestBody RefreshTokenRequest request) {
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.refresh(request));
    }

}
