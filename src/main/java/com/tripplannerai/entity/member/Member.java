package com.tripplannerai.entity.member;

import com.tripplannerai.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;  //이메일 겸 로그인 시 아이디

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String nickname;

    @Column(length = 11)
    private String phoneNumber;

    @Column(nullable = false, length = 10)
    private String provider;

    @Column(nullable = false, length = 50)
    private String providerId;


    @Column(name = "state", columnDefinition = "BOOLEAN DEFAULT FALSE")  //회원탈퇴여부
    private boolean isWithdrawn;

    @Column
    private String refreshToken;
}
