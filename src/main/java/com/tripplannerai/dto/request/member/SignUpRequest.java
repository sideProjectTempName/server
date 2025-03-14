package com.tripplannerai.dto.request.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpRequest {

    @NotNull(message = "email can't be null")
    private String email;
    @NotNull(message = "password can't be null")
    private String password;
    @NotNull(message = "nickname can't be null")
    private String nickname;
    @NotNull(message = "phoneNumber can't be null")
    private String phoneNumber;

}
