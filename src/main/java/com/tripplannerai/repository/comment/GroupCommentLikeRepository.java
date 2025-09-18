package com.tripplannerai.repository.comment;

import com.tripplannerai.entity.comment.GroupComment;
import com.tripplannerai.entity.comment.GroupCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupCommentLikeRepository extends JpaRepository<GroupCommentLike, Long> {



}
