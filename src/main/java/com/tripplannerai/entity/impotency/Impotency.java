package com.tripplannerai.entity.impotency;

import jakarta.persistence.*;

@Entity
public class Impotency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "imptency_key")
    private String impotencyKey;
}
