package com.tripplannerai.handler;

import com.tripplannerai.entity.member.CustomOAuth2User;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.provider.JwtProvider;
import com.tripplannerai.repository.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.tripplannerai.util.CookieUtil.getCookie;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    @Value("${jwt.refresh.expiration}")
    private int refreshExpiration;
    @Value("${jwt.access.expiration}")
    private int accessExpiration;
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
        Cookie accessTokenCookie = getCookie("accessToken", accessToken, accessExpiration);
        accessTokenCookie.setHttpOnly(false);

        Cookie refreshTokenCookie =getCookie("refreshToken",refreshToken,refreshExpiration);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        //프론트엔드로 리다이렉트
        String redirectUrl = "http://localhost:3000/oauth2/redirect" + "?status=success";
        response.sendRedirect(redirectUrl);
    }
}
