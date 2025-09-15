package com.tripplannerai.advice;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice(basePackages = {"com.tripplannerai.controller.comment"})
public class CommentAdviceController {
}
