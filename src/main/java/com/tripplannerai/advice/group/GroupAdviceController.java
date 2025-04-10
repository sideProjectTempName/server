package com.tripplannerai.advice.group;

import com.tripplannerai.controller.group.GroupController;
import com.tripplannerai.controller.payment.PaymentController;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice(basePackageClasses = GroupController.class)
public class GroupAdviceController {


}
