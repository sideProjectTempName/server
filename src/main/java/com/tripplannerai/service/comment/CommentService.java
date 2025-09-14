package com.tripplannerai.service.comment;
import com.amazonaws.services.ec2.model.transform.LaunchTemplateElasticInferenceAcceleratorStaxUnmarshaller;
import com.tripplannerai.common.comment.AlreadyCommentLikeException;
import com.tripplannerai.common.comment.NotFoundCommentException;
import com.tripplannerai.common.comment.NotFoundCommentLikeException;
import com.tripplannerai.common.exception.comment.NotFoundReceiptReviewExeption;
import com.tripplannerai.common.exception.member.NotAuthorizeException;
import com.tripplannerai.common.exception.member.NotFoundMemberException;
import com.tripplannerai.dto.request.comment.AddCommentRequest;
import com.tripplannerai.dto.request.comment.UpdateCommentRequest;
import com.tripplannerai.dto.response.comment.*;
import com.tripplannerai.entity.comment.Comment;
import com.tripplannerai.entity.comment.CommentLike;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.entity.receiptreview.ReceiptReview;
import com.tripplannerai.repository.comment.CommentLikeRepository;
import com.tripplannerai.repository.comment.CommentRepository;
import com.tripplannerai.repository.member.MemberRepository;
import com.tripplannerai.repository.receiptreview.ReceiptReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tripplannerai.util.ConstClass.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReceiptReviewRepository reviewRepository;
    public LikeCommentResponse likeComment(Long commentId, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member!!"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundCommentException("Not Found Comment!!"));
        Optional<CommentLike> optionalCommentLike = commentLikeRepository.findByCommentAndMember(comment, member);
        if(optionalCommentLike.isPresent()) throw new AlreadyCommentLikeException("Already CommentLike!!");
        CommentLike commentLike = CommentLike.of(comment, member);
        commentLikeRepository.save(commentLike);
        comment.plusCount();
        return LikeCommentResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public LikeCommentResponse deleteLikeComment(Long commentId, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member!!"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundCommentException("Not Found Comment!!"));
        CommentLike commentLike = commentLikeRepository.findByCommentAndMember(comment, member)
                .orElseThrow(() -> new NotFoundCommentLikeException("Not Found CommentLike!!"));
        commentLikeRepository.delete(commentLike);
        comment.minusCount();
        return LikeCommentResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public AddCommentResponse addComment(Long reviewId, AddCommentRequest addCommentRequest, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member!!"));
        ReceiptReview receiptReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReceiptReviewExeption("Not Found ReceiptReview!!"));
        String content = addCommentRequest.getContent();
        Long parentCommentId = addCommentRequest.getParentCommentId();
        boolean hasParent = parentCommentId != null;
        Comment parentComment = null;
        if(hasParent) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new NotFoundCommentException("Not Found Comment!!"));

        }
        Comment comment = Comment.of(member,receiptReview,content,parentComment);
        commentRepository.save(comment);
        return AddCommentResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public UpdateCommentResponse updateComment(Long reviewId, Long commentId, UpdateCommentRequest updateCommentRequest, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member!!"));
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReceiptReviewExeption("Not Found ReceiptReview!!"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundCommentException("Not Found Comment!!"));
        String content = updateCommentRequest.getContent();
        Long commentMemberId = comment.getMember().getId();
        Long memberId = member.getId();
        boolean authorize = commentMemberId.equals(memberId);
        if(!authorize) throw new NotAuthorizeException("Not Authorize!!");
        comment.changeContent(content);
        return UpdateCommentResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public DeleteCommentResponse deleteComment(Long reviewId, Long commentId, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member!!"));
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReceiptReviewExeption("Not Found ReceiptReview!!"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundCommentException("Not Found Comment!!"));
        Long commentMemberId = comment.getMember().getId();
        Long memberId = member.getId();
        boolean authorize = commentMemberId.equals(memberId);
        if(!authorize) throw new NotAuthorizeException("Not Authorize!!");
        commentRepository.delete(comment);
        return DeleteCommentResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public CommentResponse comments(Long reviewId, Long id, Integer page, Integer pageSize) {
        reviewRepository.findById(reviewId)
                .orElseThrow(()-> new NotFoundReceiptReviewExeption("Not Found Review!!"));
        memberRepository.findById(id)
                .orElseThrow(()->new NotFoundMemberException("Not Found Member!!"));
        boolean hasNext = true;
        List<CommentElement> comments = new ArrayList<>();
        return CommentResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,comments,hasNext);
    }
}
