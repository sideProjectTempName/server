package com.tripplannerai.mapper;

import com.tripplannerai.dto.request.payment.PaymentRequest;
import com.tripplannerai.dto.request.payment.TempPaymentRequest;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.entity.payment.Payment;
import com.tripplannerai.entity.payment.TempPayment;

public class PaymentFactory {
    public static Payment from(PaymentRequest paymentRequest, Member member){
        return Payment.builder()
                .member(member)
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .paymentKey(paymentRequest.getPaymentKey())
                .build();
    }

    public static TempPayment from(TempPaymentRequest tempPaymentRequest, Member member){
        return TempPayment.builder()
                .member(member)
                .orderId(tempPaymentRequest.getOrderId())
                .amount(tempPaymentRequest.getAmount())
                .build();
    }
}
