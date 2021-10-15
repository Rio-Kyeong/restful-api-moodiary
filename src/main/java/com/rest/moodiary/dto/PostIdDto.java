package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "게시글 아이디를 반환해주는 DTO")
public class PostIdDto {

    private Long postId;
}
