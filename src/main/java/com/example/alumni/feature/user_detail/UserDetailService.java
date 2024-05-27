package com.example.alumni.feature.user_detail;



import com.example.alumni.feature.user_detail.dto.AdminUpdateRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailResponse;
import com.example.alumni.utils.CustomPageUtils;

import java.io.OutputStream;
import java.util.Optional;

public interface UserDetailService {

    CustomPageUtils<UserDetailResponse> getAllUserDetails(int page, int size, String baseUrl, Optional<String> genType, Optional<String> numGen, Optional<Boolean> isGraduated, Optional<Boolean> isEmployed, Optional<String> studyAbroad, Optional<String> employType);
    void deleteUserDetail(String id);
    UserDetailResponse createUserDetail(UserDetailRequest userDetailRequest);
    UserDetailResponse updateUserDetail(String id, UserDetailRequest userDetailRequest);
    UserDetailResponse adminUpdateUserDetail(String id, AdminUpdateRequest adminUpdateRequest);
    void generateCSVReport(OutputStream out);
}
