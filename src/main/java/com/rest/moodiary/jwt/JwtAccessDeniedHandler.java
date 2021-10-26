package com.rest.moodiary.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.moodiary.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

// 인가(Authorization)에 대한 처리
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now().toString(),"관리자 권한이 없습니다.", request.getRequestURI());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        try(OutputStream os = response.getOutputStream()){
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(os, exceptionResponse);
            os.flush();
        }
    }
}
