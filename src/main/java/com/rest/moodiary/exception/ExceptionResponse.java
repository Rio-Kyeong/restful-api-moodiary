package com.rest.moodiary.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * 예외 응답 클래스
 */
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
