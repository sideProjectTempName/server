package com.tripplannerai.mapper.member;

import com.tripplannerai.dto.request.member.SignUpRequest;
import com.tripplannerai.entity.image.Image;
import com.tripplannerai.entity.member.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MemberFactory {

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static Member of(SignUpRequest signUpRequest, Image image){
        return Member.builder()
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .image(image)
                .build();
    }
}
