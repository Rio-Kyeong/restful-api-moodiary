package com.rest.moodiary.dto;

import com.rest.moodiary.entity.Authority;
import com.rest.moodiary.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인된 사용자의 정보를 반환하는 DTO")
public class LoginUserDto {

    private Long userId;

    private String userName;

    private Set<Authority> authorities;

    public LoginUserDto(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.authorities = user.getAuthorities();
    }
}
