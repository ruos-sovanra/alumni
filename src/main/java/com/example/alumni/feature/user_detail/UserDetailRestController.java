package com.example.alumni.feature.user_detail;


import com.example.alumni.feature.user_detail.dto.AdminUpdateRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailResponse;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user-details")
@RequiredArgsConstructor
public class UserDetailRestController {

    private final UserDetailService userDetailService;

    @GetMapping
    @Operation(summary = "Get all user details")
    public ResponseEntity<CustomPageUtils<UserDetailResponse>> getAllUserDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,@RequestParam(required = false) Optional<String> genType,
            @RequestParam(required = false) Optional<String> numGen,
            @RequestParam(required = false) Optional<Boolean> isGraduated,
            @RequestParam(required = false) Optional<Boolean> isEmployed,
            @RequestParam(required = false) Optional<String> country,
            @RequestParam(required = false) Optional<String> employType,
            HttpServletRequest request) {
        String baseUrl =request.getScheme()+ "://"+ request.getServerName() + ":" + request.getServerPort() +  "/api/v1/user-details";
        CustomPageUtils<UserDetailResponse> userDetailResponseCustomPage = userDetailService.getAllUserDetails(page, size, baseUrl, genType, numGen, isGraduated, isEmployed, country, employType);
        return ResponseEntity.ok(userDetailResponseCustomPage);
    }

    @PostMapping
    @Operation(summary = "Create user detail")
    public BaseResponse<UserDetailResponse> createUserDetail(@RequestBody UserDetailRequest userDetailRequest) {
        return BaseResponse.<UserDetailResponse>createSuccess()
                .setPayload(userDetailService.createUserDetail(userDetailRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user detail")
    public BaseResponse<UserDetailResponse> updateUserDetail(@PathVariable String id,@RequestBody UserDetailRequest userDetailRequest) {
        return BaseResponse.<UserDetailResponse>updateSuccess()
                .setPayload(userDetailService.updateUserDetail(id, userDetailRequest));
    }

    @PutMapping("/admin/{id}")
    @Operation(summary = "Admin update user detail")
    public BaseResponse<UserDetailResponse> adminUpdateUserDetail(@PathVariable String id,@RequestBody AdminUpdateRequest adminUpdateRequest) {
        return BaseResponse.<UserDetailResponse>updateSuccess()
                .setPayload(userDetailService.adminUpdateUserDetail(id, adminUpdateRequest));
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadCSV() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        userDetailService.generateCSVReport(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=UserDetails.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(in));
    }
}
