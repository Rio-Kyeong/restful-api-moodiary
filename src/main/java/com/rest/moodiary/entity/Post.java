package com.rest.moodiary.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@ApiModel(description = "게시글 상세 정보 Entity")
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "게시글 번호")
    private Long postId;

    @Column(name = "post_mood")
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(notes = "오늘의 기분")
    private PostMood mood;

    @ApiModelProperty(notes = "게시글 제목")
    @Column(name = "post_title")
    private String title;

    @ApiModelProperty(notes = "오늘의 한마디")
    @Column(name = "post_memo")
    private String memo;

    @Column(name = "post_date")
    @ApiModelProperty(notes = "게시글 작성일")
    private LocalDate postDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(notes = "작성자")
    private User user;

    // 글 쓰기
    public void insertPost(User user, PostMood mood, String title, String memo){
        this.user = user;
        this.mood = mood;
        this.title = title;
        this.memo = memo;
        this.postDate = LocalDate.now();
    }

    // 글 수정
    public void updatePost(PostMood mood, String title, String memo){
        this.mood = mood;
        this.title = title;
        this.memo = memo;
    }
}


