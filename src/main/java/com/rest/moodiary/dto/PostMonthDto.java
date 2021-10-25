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
@ApiModel(description = "월 별 게시글을 조회하는 DTO")
public class PostMonthDto {

    @ApiModelProperty(notes = "예) yyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(notes = "예) yyy-MM-dd")
    private LocalDate endDate;

    private String username;
}
