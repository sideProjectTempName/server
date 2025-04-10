package com.tripplannerai.validator;

import com.tripplannerai.exception.member.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public Claims validateToken(String jwtToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        }catch (Exception e) {
            throw new InvalidJwtTokenException("invalid Token!!");
        }
    }
}
