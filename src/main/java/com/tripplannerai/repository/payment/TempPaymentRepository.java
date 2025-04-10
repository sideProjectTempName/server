package com.tripplannerai.repository.payment;

import com.tripplannerai.entity.member.Member;
import com.tripplannerai.entity.payment.TempPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TempPaymentRepository extends JpaRepository<TempPayment, Long> {
    Optional<TempPayment> findByOrderIdAndAmountAndMember(String orderId, Integer amount, Member member);
}
