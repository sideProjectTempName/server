package com.tripplannerai.controller.course;


import com.tripplannerai.common.annotation.Id;
import com.tripplannerai.service.course.CourseLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courseLike")
public class CourseLikeController {

    private final CourseLikeService courseLikeService;

    @PostMapping("/{courseId}")
    public ResponseEntity<Void> addLike(@PathVariable Long courseId, @Id Long userId) {
        courseLikeService.addLikeCourse(courseId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> removeLike(@PathVariable Long courseId, @Id Long userId) {
        courseLikeService.removeLikeCourse(courseId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}/liked")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long courseId, @Id Long userId) {
        boolean liked = courseLikeService.isLiked(courseId, userId);
        return ResponseEntity.ok(liked);
    }




}
