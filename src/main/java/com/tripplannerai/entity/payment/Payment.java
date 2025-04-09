package com.tripplannerai.entity.payment;

import com.tripplannerai.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(name = "payment_key")
    private String paymentKey;
    private Integer amount;
    @Column(name = "order_id")
    private String orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
