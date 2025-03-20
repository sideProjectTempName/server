package com.tripplannerai.advice.member;

import com.tripplannerai.controller.member.MemberController;
import com.tripplannerai.dto.response.ErrorResponse;
import com.tripplannerai.exception.member.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.tripplannerai.util.ConstClass.*;

//@RestControllerAdvice
public class MemberAdviceController {

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundMemberException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_MEMBER_CODE,NOT_FOUND_MEMBER_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnCorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handleUnCorrectPasswordException() {
        return new ResponseEntity<>(ErrorResponse.of(UN_CORRECT_PASSWORD_CODE,UN_CORRECT_PASSWORD_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotAuthorizeException.class)
    public ResponseEntity<ErrorResponse> handleNotAuthorizaException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_AUTHORIZED_CODE,NOT_AUTHORIZED_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MemberExistException.class)
    public ResponseEntity<ErrorResponse> handleMemberExistException() {
        return new ResponseEntity<>(ErrorResponse.of(MEMBER_EXISTS_CODE,MEMBER_EXISTS_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundCertificationException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundCertificationException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_CERTIFICATION_CODE,NOT_FOUND_CERTIFICATION_MESSAGE), HttpStatus.BAD_REQUEST);
    }
}
