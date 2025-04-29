package com.tripplannerai.service.course;

public interface CourseLikeService {
    void addLikeCourse(Long courseId, Long userId);
    void removeLikeCourse(Long courseId, Long userId);

    boolean isLiked(Long courseId, Long userId);
}
