package com.tripplannerai.controller.coment;

import com.tripplannerai.common.annotation.Id;
import com.tripplannerai.dto.request.comment.AddCommentRequest;
import com.tripplannerai.dto.request.comment.UpdateCommentRequest;
import com.tripplannerai.dto.response.comment.*;
import com.tripplannerai.service.comment.CommentService;
import com.tripplannerai.service.comment.GroupCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("")
@RequiredArgsConstructor
public class GroupCommentController {
    private final GroupCommentService groupCommentService;
    @PostMapping("/api/group/{groupId}/comment/{parentCommentId}")
    public ResponseEntity<AddCommentResponse> addComment(@PathVariable Long groupId
            , @RequestBody AddCommentRequest addCommentRequest
            ,@Id Long id,Long parentCommentId){
        AddCommentResponse addCommentResponse = groupCommentService.addComment(groupId,addCommentRequest,id,parentCommentId);
        return new ResponseEntity<>(addCommentResponse, HttpStatus.OK);
    }
    @PutMapping("/api/review/{reviewId}/comment/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long reviewId, @PathVariable Long commentId,
                                                               @RequestBody UpdateCommentRequest updateCommentRequest, @Id Long id){
        UpdateCommentResponse updateCommentResponse = commentService.updateComment(reviewId,commentId,updateCommentRequest,id);
        return new ResponseEntity<>(updateCommentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/api/review/{reviewId}/comment/{commentId}")
    public ResponseEntity<DeleteCommentResponse> updateComment(@PathVariable Long reviewId, @PathVariable Long commentId,
                                                               @Id Long id){
        DeleteCommentResponse deleteCommentResponse = commentService.deleteComment(reviewId,commentId,id);
        return new ResponseEntity<>(deleteCommentResponse, HttpStatus.OK);
    }

    @GetMapping("/api/review/{reviewId}/comments")
    public ResponseEntity<CommentResponse> comments(@PathVariable Long reviewId,@Id Long id
            ,@RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer pageSize){
        CommentResponse commentResponse = commentService.comments(reviewId,id,page,pageSize);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @PostMapping("/api/review/comment/{commentId}/like")
    public ResponseEntity<LikeCommentResponse> likeComment(@PathVariable Long commentId, @Id Long id){
        LikeCommentResponse likeCommentResponse = commentService.likeComment(commentId,id);
        return new ResponseEntity<>(likeCommentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/api/review/comment/{commentId}/like")
    public ResponseEntity<LikeCommentResponse> deleteLikeComment(@PathVariable Long commentId, @Id Long id){
        LikeCommentResponse likeCommentResponse = commentService.deleteLikeComment(commentId,id);
        return new ResponseEntity<>(likeCommentResponse, HttpStatus.OK);
    }



}
