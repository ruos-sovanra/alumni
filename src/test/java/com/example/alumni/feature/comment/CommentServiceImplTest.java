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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private SocialRepository socialRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComment() {
        CommentRequest commentRequest = new CommentRequest("userId", "socialId", "comment");
        Comment comment = new Comment();
        User user = new User();
        Social social = new Social();
        CommentResponse commentResponse = new CommentResponse("id", "userId", "socialId", null, "comment", false);

        when(userRepository.findById(any(String.class))).thenReturn(Optional.of(user));
        when(socialRepository.findById(any(String.class))).thenReturn(Optional.of(social));
        when(commentMapper.fromRequestToResponse(any(CommentRequest.class))).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);

        CommentResponse result = commentService.createComment(commentRequest);

        assertEquals(commentResponse, result);
    }

    @Test
    public void testUpdateComment() {
        CommentRequest commentRequest = new CommentRequest("userId", "socialId", "updated comment");
        Comment comment = new Comment();
        User user = new User();
        Social social = new Social();
        CommentResponse commentResponse = new CommentResponse("id", "userId", "socialId", null, "updated comment", false);

        when(commentRepository.findById(any(String.class))).thenReturn(Optional.of(comment));
        when(userRepository.findById(any(String.class))).thenReturn(Optional.of(user));
        when(socialRepository.findById(any(String.class))).thenReturn(Optional.of(social));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);

        CommentResponse result = commentService.updateComment("id", commentRequest);

        assertEquals(commentResponse, result);
    }

    @Test
    public void testDeleteComment() {
        Comment comment = new Comment();

        when(commentRepository.findById(any(String.class))).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).delete(any(Comment.class));

        commentService.deleteComment("id");

        verify(commentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    public void testCreateRepliedComment() {
        RepliedRequest repliedRequest = new RepliedRequest("userId", "socialId", "parentCommentId", "reply comment");
        Comment parentComment = new Comment();
        Comment comment = new Comment();
        User user = new User();
        Social social = new Social();
        CommentResponse commentResponse = new CommentResponse("id", "userId", "socialId", null, "reply comment", true);

        when(commentRepository.findById(any(String.class))).thenReturn(Optional.of(parentComment));
        when(commentMapper.responseToComment(any(RepliedRequest.class), any(Comment.class))).thenReturn(comment);
        when(userRepository.findById(any(String.class))).thenReturn(Optional.of(user));
        when(socialRepository.findById(any(String.class))).thenReturn(Optional.of(social));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);

        CommentResponse result = commentService.createRepliedComment(repliedRequest);

        assertEquals(commentResponse, result);
    }

    // Similar tests for other methods
}