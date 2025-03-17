package com.tripplannerai.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.dto.response.member.SignInResponse;
import com.tripplannerai.entity.member.CustomOAuth2User;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.provider.JwtProvider;
import com.tripplannerai.repository.member.MemberRepository;
import com.tripplannerai.util.ConstClass;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

import static com.tripplannerai.util.ConstClass.SUCCESS_CODE;
import static com.tripplannerai.util.ConstClass.SUCCESS_MESSAGE;
import static com.tripplannerai.util.CookieUtil.getCookie;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    @Value("${jwt.refresh.expiration}")
    private int refreshExpiration;
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2User.getName();
        String accessToken = jwtProvider.createAccessToken(userId,"user");
        String refreshToken = jwtProvider.createRefreshToken(userId,"user");

        // DB에 refreshToken 저장
        Member member = memberRepository.findByEmail(userId)
                .orElseThrow(() -> new NotFoundMemberException("Member not found in OAuth2"));
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        // 쿠키 설정
        Cookie refreshTokenCookie =getCookie("refreshToken",refreshToken,refreshExpiration);
        response.addCookie(refreshTokenCookie);

        // SignInResponse 응답
        SignInResponse signInResponse = SignInResponse.of(SUCCESS_CODE, SUCCESS_MESSAGE, accessToken);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new ObjectMapper().writeValueAsString(signInResponse));
        response.getWriter().flush();
    }
}
