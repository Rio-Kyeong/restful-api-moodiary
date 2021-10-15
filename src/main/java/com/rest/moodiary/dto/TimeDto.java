package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "현재 시간을 반환하는 DTO")
public class TimeDto {

    private String time;
}
