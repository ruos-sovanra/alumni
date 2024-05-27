package com.example.alumni.mapper;


import com.example.alumni.domain.ShareComment;
import com.example.alumni.feature.share_comment.dto.ShareCommentRequest;
import com.example.alumni.feature.share_comment.dto.ShareCommentResponse;
import com.example.alumni.feature.share_comment.dto.ShareRepliedRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ShareCommentMapper {

    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "replies", source = "replies")
    @Mapping(target = "parentCommentId", expression = "java(shareComment.getParentComment() != null ? shareComment.getParentComment().getId() : null)")
    @Mapping(target = "replied", expression = "java(shareComment.getParentComment() != null)")
    public abstract ShareCommentResponse toShareCommentResponse(ShareComment shareComment);

    public abstract ShareComment fromRequestToResponse(ShareCommentRequest shareCommentRequest);

    @AfterMapping
    protected void handleNestedComments(ShareComment shareComment, @MappingTarget ShareCommentResponse response) {
        if (shareComment.getParentComment() != null ) {
            List<ShareCommentResponse> replies = shareComment.getReplies().stream()
                    .map(this::toShareCommentResponse)
                    .collect(Collectors.toList());
            ShareCommentResponse newResponse = ShareCommentResponse.withNestedComment(shareComment, response, replies);
            response = newResponse;
        }
    }

    public ShareComment responseToComment(ShareRepliedRequest shareCommentRequest, ShareComment parentComment) {
        ShareComment shareComment = new ShareComment();
        shareComment.setParentComment(parentComment);
        return shareComment;
    }

    public abstract List<ShareCommentResponse> commentListToCommentResponseList(List<ShareComment> shareComments);
}