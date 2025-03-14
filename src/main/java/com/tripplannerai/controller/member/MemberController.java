package com.tripplannerai.controller.member;

import com.tripplannerai.dto.request.member.SignInRequest;
import com.tripplannerai.dto.request.member.SignUpRequest;
import com.tripplannerai.dto.response.member.SignInResponse;
import com.tripplannerai.dto.response.member.SignUpResponse;
import com.tripplannerai.service.member.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/auth/login")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        SignInResponse signInResponse = memberService.signIn(signInRequest,response);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }
    @PostMapping(value = "/auth/sign-up",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest, @RequestPart MultipartFile file){
        SignUpResponse signUpResponse = memberService.signUp(signUpRequest,file);
        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }
}
