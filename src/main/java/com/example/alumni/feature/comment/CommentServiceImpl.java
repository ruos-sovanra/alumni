package com.example.alumni.feature.comment;


import com.example.alumni.domain.Comment;
import com.example.alumni.domain.Social;
import com.example.alumni.domain.User;
import com.example.alumni.feature.comment.dto.CommentRequest;
import com.example.alumni.feature.comment.dto.CommentResponse;
import com.example.alumni.feature.comment.dto.RepliedRequest;
import com.example.alumni.feature.social.SocialRepository;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.CommentMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final SocialRepository socialRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;


    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {

        Comment comment = commentMapper.fromRequestToResponse(commentRequest);
        User user = userRepository.findById(commentRequest.userId()).orElseThrow(()-> new NoSuchElementException("User not found"));
        Social social = socialRepository.findById(commentRequest.socialId()).orElseThrow(()-> new NoSuchElementException("Social not found"));
        comment.setUser(user);
        comment.setSocial(social);
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public CommentResponse createRepliedComment(RepliedRequest repliedRequest) {
        Comment parentComment = commentRepository.findById(repliedRequest.parentCommentId()).orElseThrow(()-> new NoSuchElementException("Parent comment not found"));
        Comment comment = commentMapper.responseToComment(repliedRequest, parentComment);
        User user = userRepository.findById(repliedRequest.userId()).orElseThrow(()-> new NoSuchElementException("User not found"));
        Social social = socialRepository.findById(repliedRequest.socialId()).orElseThrow(()-> new NoSuchElementException("Social not found"));
        comment.setUser(user);
        comment.setSocial(social);
        comment.setComment(repliedRequest.comment());
        commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public CommentResponse getComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("Comment not found"));

        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public CommentResponse updateComment(String commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("Comment not found"));
        User user = userRepository.findById(commentRequest.userId()).orElseThrow(()-> new NoSuchElementException("User not found"));
        Social social = socialRepository.findById(commentRequest.socialId()).orElseThrow(()-> new NoSuchElementException("Social not found"));
        comment.setUser(user);
        comment.setSocial(social);
        comment.setComment(commentRequest.comment());
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(()-> new NoSuchElementException("Comment not found"));
        commentRepository.delete(comment);

    }

    @Override
    public List<CommentResponse> getCommentsBySocialId(String socialId) {
        List<Comment> comments = commentRepository.findBySocialId(socialId);
        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }

    @Override
    public List<CommentResponse> getCommentsByUserId(String userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);

        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }

    @Override
    public List<CommentResponse> getCommentsByParentComment(String parentCommentId) {
        List<Comment> comments = commentRepository.findByParentCommentId(parentCommentId);
        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }

    @Override
    public CustomPageUtils<CommentResponse> getComments(int page, int size, String baseUrl) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Comment> comments = commentRepository.findAll(pageable);

        // Convert to CommentResponse and filter out comments that are replies
        List<CommentResponse> commentResponses = comments.stream()
                .map(commentMapper::toCommentResponse)
                .filter(commentResponse -> commentResponse.parentCommentId() == null)
                .collect(Collectors.toList());

        // Create a new PageImpl with the filtered comments
        Page<CommentResponse> filteredComments = new PageImpl<>(commentResponses, pageable, commentResponses.size());

        return CustomPagination(filteredComments, baseUrl);
    }

    public CustomPageUtils<CommentResponse> CustomPagination(Page<CommentResponse> page, String baseUrl){
        CustomPageUtils<CommentResponse> customPage = new CustomPageUtils<>();
        if(page.hasNext()){
            customPage.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }
        if (page.hasPrevious()){
            customPage.setPrevious(baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize());
        }
        customPage.setTotal((int) page.getTotalElements());
        customPage.setResults(page.getContent());
        return customPage;
    }


}