package com.tripplannerai.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.dto.response.ErrorResponse;
import com.tripplannerai.util.ConstClass;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.tripplannerai.util.ConstClass.*;

@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String error = request.getParameter("error");
        ErrorResponse errorResponse;

        if (error.equals("access_denied")) {
            errorResponse = ErrorResponse.of(OAUTH_DENIED_CODE, OAUTH_DENIED_MESSAGE);
        } else if (error.equals("invalid_client")) {
            errorResponse = ErrorResponse.of(OAUTH_INVALID_CLIENT_CODE, OAUTH_INVALID_CLIENT_MESSAGE);
        } else if (error.equals("server_error")) {
            errorResponse = ErrorResponse.of(OAUTH_SERVER_ERROR_CODE, OAUTH_SERVER_ERROR_MESSAGE);
        } else {
            errorResponse = ErrorResponse.of(AUTHENTICATION_FAILED_CODE,AUTHENTICATION_FAILED_MESSAGE);
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        response.getWriter().flush();
    }
}
