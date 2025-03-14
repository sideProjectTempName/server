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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInRequest {
    @NotNull(message = "Email can't be null")
    private String email;
    @NotNull(message = "Password can't be null")
    private String password;
}
