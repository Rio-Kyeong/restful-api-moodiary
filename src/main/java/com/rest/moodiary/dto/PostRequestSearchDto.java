package com.rest.moodiary.dto;

import com.rest.moodiary.entity.PostMood;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "요청된 글 제목과 기분을 받는 DTO")
public class PostRequestSearchDto {

    private String title; // 글 제목

    private PostMood mood; // 기분
}