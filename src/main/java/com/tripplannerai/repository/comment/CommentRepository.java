package com.tripplannerai.repository.comment;

import com.tripplannerai.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
