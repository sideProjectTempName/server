package com.tripplannerai.advice;

import com.tripplannerai.dto.response.ErrorResponse;
import com.tripplannerai.exception.member.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.tripplannerai.util.ConstClass.*;
@Order(2)
@RestControllerAdvice
public class AdviceController {
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException() {
        return new ResponseEntity<>(ErrorResponse.of(VALIDATION_FAILED_CODE,VALIDATION_FAILED_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException
            (HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ErrorResponse.of(METHOD_NOT_SUPPORTED_CODE,METHOD_NOT_SUPPORTED_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(IOException ex) {
        return new ResponseEntity<>(ErrorResponse.of(DB_ERROR_CODE,DB_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
