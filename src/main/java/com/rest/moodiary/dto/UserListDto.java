package com.rest.moodiary.dto;

import com.rest.moodiary.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원의 리스트를 반환하는 DTO")
public class UserListDto {

    private Long userId;
    private String userName;
    private LocalDate joinDate;
    private boolean activated;

    public UserListDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.joinDate = user.getJoinDate();
        this.activated = user.isActivated();
    }
}