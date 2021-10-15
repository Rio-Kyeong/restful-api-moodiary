package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인 시 사용자 정보를 받는 DTO")
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50, message = "이름(ID)은 3자이상 50자 이하로 입력해주세요.")
    private String userName;

    @NotNull
    @Size(min = 3, max = 100, message = "비밀번호는 3자이상 100자 이하로 입력해주세요.")
    private String password;
}
