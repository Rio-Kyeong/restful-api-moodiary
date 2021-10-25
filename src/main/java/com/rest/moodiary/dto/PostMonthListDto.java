package com.rest.moodiary.dto;

import com.rest.moodiary.entity.Post;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "날짜 별 게시글 리스트를 반환하는 DTO")
public class PostMonthListDto {

    private Long postId;
    private LocalDate postDate;

    public PostMonthListDto(Post post) {
        this.postId = post.getPostId();
        this.postDate = post.getPostDate();
    }
}
