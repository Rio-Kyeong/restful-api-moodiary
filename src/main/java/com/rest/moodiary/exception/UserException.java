package com.rest.moodiary.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 회원가입 중복 예외처리
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }
}
