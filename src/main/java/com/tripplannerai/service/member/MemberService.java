package com.tripplannerai.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.dto.JwtSubject;
import com.tripplannerai.dto.request.member.EmailCheckoutRequest;
import com.tripplannerai.dto.request.member.SignInRequest;
import com.tripplannerai.dto.request.member.SignUpRequest;
import com.tripplannerai.dto.response.member.EmailCheckoutResponse;
import com.tripplannerai.dto.response.member.SignInResponse;
import com.tripplannerai.dto.response.member.SignUpResponse;
import com.tripplannerai.entity.image.Image;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.exception.member.MemberExistException;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.exception.member.UnCorrectPasswordException;
import com.tripplannerai.mapper.image.ImageFactory;
import com.tripplannerai.mapper.member.MemberFactory;
import com.tripplannerai.repository.image.ImageRepository;
import com.tripplannerai.repository.member.MemberRepository;
import com.tripplannerai.provider.JwtProvider;
import com.tripplannerai.s3.S3UploadService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.tripplannerai.mapper.member.MemberFactory.*;
import static com.tripplannerai.mapper.image.ImageFactory.*;
import static com.tripplannerai.util.ConstClass.SUCCESS_CODE;
import static com.tripplannerai.util.ConstClass.SUCCESS_MESSAGE;
import static com.tripplannerai.util.CookieUtil.getCookie;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    @Value("${jwt.refresh.expiration}")
    private int refreshExpiration;
    private ObjectMapper objectMapper = new ObjectMapper();

    public SignInResponse signIn(SignInRequest signInRequest, HttpServletResponse response) throws JsonProcessingException {

        Member member = memberRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()-> new NotFoundMemberException("not Found Member"));
        boolean matches = passwordEncoder.matches(signInRequest.getPassword(), member.getPassword());
        if(!matches){
            throw new UnCorrectPasswordException("doesn't match Password!");
        }
        JwtSubject jwtSubject = JwtSubject.of(member);
        String accessToken = jwtProvider.createAccessToken(objectMapper.writeValueAsString(jwtSubject),"user");
        String refreshToken = jwtProvider.createRefreshToken(objectMapper.writeValueAsString(jwtSubject),"user");
        Cookie cookie = getCookie("refreshToken",refreshToken,refreshExpiration);
        response.addCookie(cookie);
        member.setRefreshToken(refreshToken);
        return SignInResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,accessToken);
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest){


            Member member = of(signUpRequest);
            memberRepository.save(member);
            return SignUpResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public EmailCheckoutResponse emailCheck(@Valid EmailCheckoutRequest emailCheckoutRequest) {
        Optional<Member> optionalMember = memberRepository.findByEmail(emailCheckoutRequest.getEmail());
        if(optionalMember.isPresent()){
            throw new MemberExistException("member exist");
        }
        return EmailCheckoutResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }
}
