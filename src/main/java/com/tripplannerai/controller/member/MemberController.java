package com.tripplannerai.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tripplannerai.annotation.Username;
import com.tripplannerai.dto.request.EmailCertificationRequest;
import com.tripplannerai.dto.request.member.EmailCheckoutRequest;
import com.tripplannerai.dto.request.member.SignInRequest;
import com.tripplannerai.dto.request.member.SignUpRequest;
import com.tripplannerai.dto.response.member.*;
import com.tripplannerai.service.member.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/auth/login")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest, HttpServletResponse response) throws JsonProcessingException {
        SignInResponse signInResponse = memberService.signIn(signInRequest,response);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }
    @PostMapping(value = "/auth/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = memberService.signUp(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }
    @PostMapping(value = "/email-check")
    public ResponseEntity<EmailCheckoutResponse> emailCheck(@Valid @RequestBody EmailCheckoutRequest emailCheckoutRequest) {
        EmailCheckoutResponse emailCheckoutResponse = memberService.emailCheck(emailCheckoutRequest);
        return new ResponseEntity<>(emailCheckoutResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/certification")
    public ResponseEntity<SendCertificationResponse> sendCertification(@Valid @RequestBody EmailCertificationRequest emailCertificationRequest) {
        SendCertificationResponse sendCertificationResponse = memberService.sendCertification(emailCertificationRequest);
        return new ResponseEntity<>(sendCertificationResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/certification")
    public ResponseEntity<CheckCertificationResponse> checkCertification(@RequestParam String email) {
        CheckCertificationResponse checkCertificationResponse = memberService.checkCertification(email);
        return new ResponseEntity<>(checkCertificationResponse, HttpStatus.OK);
    }
}
