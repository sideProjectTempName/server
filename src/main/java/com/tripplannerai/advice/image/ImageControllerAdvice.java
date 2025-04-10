package com.tripplannerai.advice.image;

import com.tripplannerai.controller.image.ImageController;
import com.tripplannerai.dto.response.ErrorResponse;
import com.tripplannerai.exception.image.NotFoundImageException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.tripplannerai.util.ConstClass.*;
@Order(1)
@RestControllerAdvice(basePackageClasses = ImageController.class)
public class ImageControllerAdvice {
    @ExceptionHandler(NotFoundImageException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundImageException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_IMAGE_CODE,NOT_FOUND_IMAGE_MESSAGE), HttpStatus.NOT_FOUND);
    }

}
