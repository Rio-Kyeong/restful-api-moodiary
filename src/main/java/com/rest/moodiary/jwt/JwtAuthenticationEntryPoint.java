package com.rest.moodiary.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.moodiary.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

// 인증(Authentication)에 대한 처리
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now().toString(),"로그인 시도 후 다시 시도해주세요.", request.getRequestURI());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        try(OutputStream os = response.getOutputStream()){
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(os, exceptionResponse);
            os.flush();
        }
    }
}
