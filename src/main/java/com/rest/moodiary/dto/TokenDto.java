package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인 시 액세스 토큰을 반환하는 DTO")
public class TokenDto {

    private String token;
}
