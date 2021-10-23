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
@ApiModel(description = "게시글 리스트를 반환하는 DTO")
public class PostAllListDto {

    private Long postId;
    private String title;
    private LocalDate postDate;

    public PostAllListDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.postDate = post.getPostDate();
    }
}
