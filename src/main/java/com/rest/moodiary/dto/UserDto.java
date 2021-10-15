package com.rest.moodiary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원가입 시 요청받은 회원의 정보를 받는 DTO")
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50, message = "이름(ID)은 3자이상 50자 이하로 입력해주세요.")
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100, message = "비밀번호는 3자이상 100자 이하로 입력해주세요.")
    private String password;
}