package com.tripplannerai.repository.comment;

import com.tripplannerai.dto.response.comment.CommentElement;
import com.tripplannerai.entity.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select new com.tripplannerai.dto.response.comment.CommentElement" +
            "(c.id,c.content,m.id,m.nickname,c.createdAt,c.updatedAt,c.isDeleted,c.count) " +
            "from Comment c " +
            "left join c.member m " +
            "left join c.receiptReview r " +
            "where r.receiptReviewId = :reviewId")
    Page<CommentElement> findCommentsByReceiptReview(Long reviewId, Pageable pageable);


}
