package com.tripplannerai.dto.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailCheckoutRequest {
    @NotNull(message = "Email can't be null")
    @Email
    private String email;
}
