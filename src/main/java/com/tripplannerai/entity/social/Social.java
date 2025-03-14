package com.tripplannerai.entity.social;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_id")
    private Long socialId;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

}
