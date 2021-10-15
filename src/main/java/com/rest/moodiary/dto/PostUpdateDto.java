package com.rest.moodiary.dto;

import com.rest.moodiary.entity.PostMood;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "게시글 작성과 수정 시 요청받은 게시글 정보를 받는 DTO")
public class PostUpdateDto {

    @NotNull(message = "오늘의 기분을 선택하세요.")
    private PostMood mood;

    @NotNull(message = "제목을 입력하세요.")
    @Size(max = 50)
    private String title;

    @NotNull(message = "오늘의 한마디를 입력하세요.")
    @Size(max = 100, message = "100자 이하로 입력해주세요.")
    private String memo;
}
