package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "페이징 값을 받는 DTO")
public class PageDto {

    @ApiModelProperty(notes = "조회 시작 위치(default : 0)")
    private int offset;

    @ApiModelProperty(notes = "조회할 데이터 수(default : 10)")
    private int limit;
}
