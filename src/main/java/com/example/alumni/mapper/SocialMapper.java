package com.example.alumni.mapper;


import com.example.alumni.domain.Social;
import com.example.alumni.feature.social.dto.PostRequest;
import com.example.alumni.feature.social.dto.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SocialMapper {

    @Named("toPostResponse")
    @Mapping(target = "postTypeName", source = "postType.type")
    PostResponse toPostResponse(Social social);

    Social toSocial(PostRequest postRequest);
}
