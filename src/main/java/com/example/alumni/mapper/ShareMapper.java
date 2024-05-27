package com.example.alumni.mapper;

import com.example.alumni.domain.Share;
import com.example.alumni.feature.share.dto.ShareRequest;
import com.example.alumni.feature.share.dto.ShareResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {SocialMapper.class})
public interface ShareMapper {


    Share requestToEntity(ShareRequest request);
    @Mapping(target = "post", source = "social", qualifiedByName = "toPostResponse")

    ShareResponse entityToResponse(Share share);
}
