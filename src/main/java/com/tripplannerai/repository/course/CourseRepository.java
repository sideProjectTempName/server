package com.tripplannerai.repository.course;

import com.tripplannerai.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query(value = "SELECT * FROM course ORDER BY RANDOM() LIMIT 8", nativeQuery = true)
    List<Course> findRandomCourses();

    @Query(value = " update course set like_count = like_count + :val where course_id = :courseId", nativeQuery = true)
    @Modifying
    void updateLikeCount(int val, Long courseId);

}
