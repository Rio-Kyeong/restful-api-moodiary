package com.rest.moodiary.service;

import com.rest.moodiary.dto.*;
import com.rest.moodiary.entity.Post;
import com.rest.moodiary.entity.User;
import com.rest.moodiary.exception.NotFoundException;
import com.rest.moodiary.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 글 작성
    @Transactional
    public Long savePost(User user, @Valid PostUpdateDto postDto){

        Post post = new Post();
        post.insertPost(user ,postDto.getMood(), postDto.getTitle(), postDto.getMemo());

        return postRepository.save(post);
    }

    // 글 삭제
    @Transactional
    public void deletePost(Post findPost){

        if(findPost == null){
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }
        postRepository.delete(findPost);
    }

    // 글 수정(dirty checking)
    @Transactional
    public Post updatePost(Long postId, PostUpdateDto postUpdateDto){

        Post findPost = postRepository.findOne(postId);

        findPost.updatePost(postUpdateDto.getMood(), postUpdateDto.getTitle(), postUpdateDto.getMemo());

        return findPost;
    }

    // 글 단건 조회
    @Transactional(readOnly = true)
    public Post findOne(Long postId){

        Post findPost = postRepository.findOne(postId);

        if(findPost == null){
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }
        return findPost;
    }

    // 회원 전체 글 리스트 조회
    @Transactional(readOnly = true)
    public List<Post> findAll(String userName){

        List<Post> findList = postRepository.findAll(userName);

        if(findList.isEmpty()){
            throw new NotFoundException("작성된 게시글이 없습니다.");
        }
        return findList;
    }

    // 월 별 글 리스트 조회
    @Transactional(readOnly = true)
    public List<Post> findMonthAll(PostMonthDto request){

        List<Post> findList = postRepository.findMonthAll(request);

        if(findList.isEmpty()){
            throw new NotFoundException("작성된 게시글이 없습니다.");
        }
        return findList;
    }

    // 날짜별 글 리스트 조회(페이징)
    @Transactional(readOnly = true)
    public List<Post> findDateAll(PostAllDto request, PageDto page){

        List<Post> findList = postRepository.findDateAll(request, page);

        if(findList.isEmpty()){
            throw new NotFoundException("작성된 게시글이 없습니다.");
        }
        return findList;
    }

    // 글 내용, 기분 조회(페이징)
    public List<Post> findAllMood(PostSearchDto postSearchDto, PageDto page){

        List<Post> searchList = postRepository.findAllMood(postSearchDto, page);

        if(searchList.isEmpty()){
            throw new NotFoundException("검색된 내용이 없습니다.");
        }
        return searchList;
    }

    // 현재 시간 반환
    @Transactional(readOnly = true)
    public String getTime(){

        LocalTime localTime = LocalTime.now();
        String ampm;

        if(localTime.get(ChronoField.AMPM_OF_DAY) == 0){
            ampm = "오전";
        }else {
            ampm = "오후";
        }
        return ampm+" "+localTime.get(ChronoField.HOUR_OF_AMPM)+"시 "+localTime.get(ChronoField.MINUTE_OF_HOUR)+"분";
    }
}
