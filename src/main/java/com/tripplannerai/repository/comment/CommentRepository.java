package com.tripplannerai.repository.comment;

import com.tripplannerai.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = """
                    select c from comment c 
                    left join receipt_review r on r.receipt_review_id = c.receipt_review_id
                    left join member m on m.member_id = c.member_id
                    where r.receiptReviewId = :reviewId 
                    offset :offset limit :limit
            """
            ,nativeQuery = true)
    List<Comment> findCommentsByReceiptReview(Long reviewId, Integer offset, Integer limit);


}
