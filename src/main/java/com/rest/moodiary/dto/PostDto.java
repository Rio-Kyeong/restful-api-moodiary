package com.rest.moodiary.dto;

import com.rest.moodiary.entity.Post;
import com.rest.moodiary.entity.PostMood;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "게시글 정보를 반환하는 DTO")
public class PostDto {

    private Long postId;

    private PostMood mood;
    private String memo;

    private LocalDate postDate;

    private String username;

    public PostDto(Post post) {
        this.postId = post.getPostId();
        this.mood = post.getMood();
        this.memo = post.getMemo();
        this.postDate = post.getPostDate();
        this.username = post.getUser().getUserName();
    }
}
