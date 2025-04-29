package com.tripplannerai.repository.course;

import com.tripplannerai.entity.course.Course;
import com.tripplannerai.entity.courselike.CourseLike;
import com.tripplannerai.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseLikeRepository extends JpaRepository<CourseLike,Long> {
    Optional<CourseLike> findByCourseAndMember(Course course, Member member);
    boolean existsByCourseAndMember(Course course, Member member);

}
