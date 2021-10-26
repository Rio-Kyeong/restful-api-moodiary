package com.rest.moodiary.controller;

import com.rest.moodiary.dto.*;
import com.rest.moodiary.entity.Post;
import com.rest.moodiary.entity.PostMood;
import com.rest.moodiary.entity.User;
import com.rest.moodiary.service.PostService;
import com.rest.moodiary.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // 글 작성
    @ApiOperation(value = "게시글 작성")
    @PostMapping("/posts")
    public ResponseEntity<PostIdDto> savePost(@RequestBody @Valid PostUpdateDto postDto, HttpServletRequest request){

        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        Long postId = postService.savePost(user, postDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postId}")
                .buildAndExpand(postId)
                .toUri();

        return ResponseEntity.created(uri).body(new PostIdDto(postId));
    }

    // 글 삭제
    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") Long postId){

        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        Post findPost = postService.findOne(postId);

        postService.deletePost(findPost);

        return ResponseEntity.noContent().build();
    }

    // 글 수정(dirty checking)
    @ApiOperation(value = "게시글 수정")
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostIdDto> updatePost(
            @PathVariable("id") Long postId,
            @RequestBody @Valid PostUpdateDto postDto){

        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        Post updatePost = postService.updatePost(postId, postDto);

        return ResponseEntity.ok().body(new PostIdDto(updatePost.getPostId()));
    }

    // 글 단건 조회
    @ApiOperation(value = "게시글 단건 조회", notes = "게시글 번호를 받아 해당 게시글 조회")
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> findOne(@PathVariable("id") Long postId){

        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        Post findPost = postService.findOne(postId);

        return ResponseEntity.ok(new PostDto(findPost));
    }

    // 회원 전체 글 리스트 조회
    @ApiOperation(value = "회원의 게시글 전체 조회", notes = "회원의 게시글 리스트 조회")
    @GetMapping("/posts")
    public Result findAll() {

        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        List<Post> findAll = postService.findAll(user.getUserName());

        List<PostAllListDto> listFindAll = findAll.stream()
                .map(p -> new PostAllListDto(p))
                .collect(Collectors.toList());

        return new Result(listFindAll);
    }

    // 월 별 글 리스트 조회
    @ApiOperation(value = "월 별 게시글 전체 조회", notes = "원하는 날짜 범위의 게시글 리스트 조회")
    @GetMapping("/posts/month")
    public Result findMonthAll(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
            )
    {
        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        PostMonthDto request = new PostMonthDto(startDate, endDate, user.getUserName());

        List<Post> findAll = postService.findMonthAll(request);

        // Entity 를 DTO 반환
        List<PostMonthListDto> listFindAll = findAll.stream()
                .map(p -> new PostMonthListDto(p))
                .collect(Collectors.toList());

        return new Result(listFindAll);
    }

    // 날짜별 글 리스트 조회(페이징)
    @ApiOperation(value = "날짜 별 게시글 전체 조회", notes = "해당 날짜의 게시글 리스트 조회(페이징)")
    @GetMapping("/posts/date")
    public Result findDateAll(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
            )
    {
        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        PostAllDto request = new PostAllDto(date, user.getUserName());
        PageDto page = new PageDto(offset, limit);

        List<Post> findAll = postService.findDateAll(request, page);

        // Entity 를 DTO 반환
        List<PostDateListDto> listFindAll = findAll.stream()
                .map(p -> new PostDateListDto(p))
                .collect(Collectors.toList());

        return new Result(listFindAll);
    }

    // 글 내용, 기분 조회(페이징)
    @ApiOperation(value = "게시글 동적 조회", notes = "기분과 제목을 이용한 게시글 리스트 조회(페이징)")
    @GetMapping("/posts/search")
    public Result postSearch(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "mood", required = false) PostMood mood
            )
    {
        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        PostSearchDto postSearchDto = new PostSearchDto(title, mood);
        PageDto page = new PageDto(offset, limit);

        List<Post> searchList = postService.findAllMood(postSearchDto, page);

        // Entity 를 DTO 변환
        List<PostSearchListDto> responseList = searchList.stream()
                .map(p -> new PostSearchListDto(p))
                .collect(Collectors.toList());

        return new Result(responseList);
    }

    // 현재 시간 반환
    @ApiOperation(value = "현재 시간 조회", notes = "예) 오후 6시 23분")
    @GetMapping("/time")
    public ResponseEntity<TimeDto> getTime(){

        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        return ResponseEntity.ok(new TimeDto(postService.getTime()));
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "게시글 아이디를 반환해주는 DTO")
    static class PostIdDto {

        private Long postId;
    }
}