package com.tripplannerai.advice.chat;

import com.tripplannerai.controller.chat.ChatController;
import com.tripplannerai.controller.payment.PaymentController;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice(basePackageClasses = ChatController.class)
public class ChatAdviceController {


}
