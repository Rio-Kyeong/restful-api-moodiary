package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "게시글 리스트를 받기위해 요청된 날짜를 받는 DTO")
public class PostRequestAllDto {

    @ApiModelProperty(notes = "예) 2021-10-11")
    private LocalDate date;

    private String username;
}
