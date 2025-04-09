package com.tripplannerai.entity.payment;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(name = "payment_key")
    private String paymentKey;
    private Integer price;
}
