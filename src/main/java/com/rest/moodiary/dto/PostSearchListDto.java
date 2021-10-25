package com.rest.moodiary.dto;

import com.rest.moodiary.entity.Post;
import com.rest.moodiary.entity.PostMood;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "날짜 별 게시글 리스트를 반환하는 DTO")
public class PostSearchListDto {

    private Long postId;
    private String title;
    private PostMood mood;
    private LocalDate postDate;

    public PostSearchListDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.mood = post.getMood();
        this.postDate = post.getPostDate();
    }
}
