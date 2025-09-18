package com.tripplannerai.repository.comment;

import com.tripplannerai.dto.response.comment.CommentElement;
import com.tripplannerai.entity.comment.Comment;
import com.tripplannerai.entity.comment.GroupComment;
import com.tripplannerai.entity.comment.GroupCommentLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface GroupCommentRepository extends JpaRepository<GroupComment, Long> {



}
