package com.tripplannerai.advice.image;

import com.tripplannerai.controller.image.ImageController;
import com.tripplannerai.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.tripplannerai.util.ConstClass.*;

//@RestControllerAdvice(basePackageClasses = ImageController.class)
public class ImageControllerAdvice {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException() {
        return new ResponseEntity<>(ErrorResponse.of(UPLOAD_FAILED_CODE,UPLOAD_FAILED_MESSAGE), HttpStatus.BAD_REQUEST);
    }
}
