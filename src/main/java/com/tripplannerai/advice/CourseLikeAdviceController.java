package com.tripplannerai.advice;

import com.tripplannerai.common.exception.course.NotFoundCourseException;
import com.tripplannerai.common.exception.course.NotFoundCourseLikeException;
import com.tripplannerai.common.exception.member.NotFoundMemberException;
import com.tripplannerai.controller.course.CourseLikeController;
import com.tripplannerai.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.tripplannerai.util.ConstClass.*;

@Order(1)
@RestControllerAdvice(basePackageClasses = CourseLikeController.class)
public class CourseLikeAdviceController {
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> handleLikeCourseMemberException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_MEMBER_CODE, NOT_FOUND_MEMBER_MESSAGE), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundCourseException.class)
    public ResponseEntity<ErrorResponse> handleLikeCourseException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_COURSE_CODE, NOT_FOUND_COURSE_MESSAGE), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundCourseLikeException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundLikeCourse(){
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_COURSE_LIKE_CODE, NOT_FOUND_COURSE_LIKE_MESSAGE), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyLikedException() {
        return new ResponseEntity<>(ErrorResponse.of(ALREADY_LIKED_COURSE_CODE, ALREADY_LIKED_COURSE_MESSAGE), HttpStatus.BAD_REQUEST);
    }
}
