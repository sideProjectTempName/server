package com.tripplannerai.mapper.payment;

import com.tripplannerai.dto.request.payment.PaymentRequest;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.entity.payment.Payment;

public class PaymentFactory {
    public static Payment from(PaymentRequest paymentRequest, Member member){
        return Payment.builder()
                .member(member)
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .paymentKey(paymentRequest.getPaymentKey())
                .build();
    }
}
