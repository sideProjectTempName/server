package com.tripplannerai.advice.member;

import com.tripplannerai.controller.member.MemberController;
import com.tripplannerai.dto.response.ErrorResponse;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.exception.member.UnCorrectPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.tripplannerai.util.ConstClass.*;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberControllerAdvice {

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundMemberException(NotFoundMemberException ex) {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_MEMBER_CODE,NOT_FOUND_MEMBER_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnCorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handleUnCorrectPasswordException(UnCorrectPasswordException ex) {
        return new ResponseEntity<>(ErrorResponse.of(UN_CORRECT_PASSWORD_CODE,UN_CORRECT_PASSWORD_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        return new ResponseEntity<>(ErrorResponse.of(UPLOAD_FAILED_CODE,UPLOAD_FAILED_MESSAGE), HttpStatus.BAD_REQUEST);
    }
}
