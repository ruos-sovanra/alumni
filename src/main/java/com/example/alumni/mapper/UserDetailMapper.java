package com.example.alumni.mapper;

import com.example.alumni.domain.UserDetail;
import com.example.alumni.feature.user_detail.dto.UserDetailRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailMapper {

    @Mapping(target = "employTypeName", source = "employ.employType")
    @Mapping(target = "studyAbroadName", source = "studyAbroad.country")
    @Mapping(target = "userName", source = "user.username")
    UserDetailResponse toUserDetailResponse(UserDetail userDetail);
    UserDetail toUserDetail(UserDetailRequest userDetailRequest);


}
