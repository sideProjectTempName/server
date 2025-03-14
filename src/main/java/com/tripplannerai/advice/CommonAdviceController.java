package com.tripplannerai.advice;

import com.tripplannerai.dto.response.ErrorResponse;
import com.tripplannerai.exception.member.NotFoundMemberException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.tripplannerai.util.ConstClass.*;

@RestControllerAdvice
public class CommonAdviceController {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ErrorResponse.of(VALIDATION_FAILED_CODE,VALIDATION_FAILED_MESSAGE), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException
            (HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ErrorResponse.of(METHOD_NOT_SUPPORTED_CODE,METHOD_NOT_SUPPORTED_MESSAGE), HttpStatus.BAD_REQUEST);
    }

}
