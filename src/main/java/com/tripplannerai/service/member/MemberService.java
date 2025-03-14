package com.tripplannerai.service.member;

import com.tripplannerai.dto.request.member.SignInRequest;
import com.tripplannerai.dto.request.member.SignUpRequest;
import com.tripplannerai.dto.response.member.SignInResponse;
import com.tripplannerai.dto.response.member.SignUpResponse;
import com.tripplannerai.entity.Member;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.exception.member.UnCorrectPasswordException;
import com.tripplannerai.repository.member.MemberRepository;
import com.tripplannerai.provider.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public SignInResponse signIn(SignInRequest signInRequest, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()-> new NotFoundMemberException("not Found Member"));
        boolean matches = passwordEncoder.matches(signInRequest.getPassword(), member.getPassword());
        if(!matches){
            throw new UnCorrectPasswordException("doesn't match Password!");
        }
        String accessToken = jwtProvider.createAccessToken(member.getEmail(),"user");
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(),"user");
        getCookie("refreshToken",refreshToken,refreshExpiration);
        member.setRefreshToken(refreshToken);
        return SignInResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,accessToken);
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest, MultipartFile file) {
        return null;
    }
}
