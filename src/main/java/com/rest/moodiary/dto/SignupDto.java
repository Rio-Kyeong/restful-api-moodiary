package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원가입된 회원의 정보를 반환하는 DTO")
public class SignupDto {

    private String username;

    private LocalDate joinDate;
}
