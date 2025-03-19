package com.tripplannerai.service.oauth;

import com.tripplannerai.entity.member.CustomOAuth2User;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.entity.social.Social;
import com.tripplannerai.entity.social.SocialType;
import com.tripplannerai.repository.member.MemberRepository;
import com.tripplannerai.repository.member.SocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();
        String email = null;
        String nickname = null;

        // 소셜 제공자별 userId와 nickname 설정
        if (oauthClientName.equals("kakao")) {
            Map<String, String> properties = (Map<String, String>) oAuth2User.getAttributes().get("properties");
            email = "kakao_" + oAuth2User.getAttributes().get("id");
            nickname = properties.get("nickname");
        } else if (oauthClientName.equals("naver")) {
            Map<String, String> response = (Map<String, String>) oAuth2User.getAttributes().get("response");
            email = "naver_" + response.get("id").substring(0, 14);
            nickname = response.get("nickname");
        } else if (oauthClientName.equals("google")) {
            email = "google_" + oAuth2User.getAttributes().get("sub");
            nickname = (String) oAuth2User.getAttributes().get("name");
        } else {
            throw new OAuth2AuthenticationException("Unsupported OAuth provider: " + oauthClientName);
        }

        // 기존 회원 조회
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        Member member;
        if (existingMember.isPresent()) {
            // 이미 존재하는 경우 기존 회원 사용
            member = existingMember.get();
        } else {
            // 새로운 회원 생성 및 저장
            member = Member.builder()
                    .email(email)
                    .nickname(nickname)
                    .password("password")
                    .build();
            memberRepository.save(member);

            SocialType socialType = switch (oauthClientName) {
                case "kakao" -> SocialType.KAKAO;
                case "naver" -> SocialType.NAVER;
                case "google" -> SocialType.GOOGLE;
                default -> throw new IllegalStateException("Unexpected provider: " + oauthClientName);
            };
            Social social = Social.builder()
                    .socialType(socialType)
                    .member(member)
                    .build();
            socialRepository.save(social);
        }

        return new CustomOAuth2User(email);
    }
}
