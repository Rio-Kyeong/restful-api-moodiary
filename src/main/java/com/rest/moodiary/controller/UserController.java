package com.rest.moodiary.controller;

import com.rest.moodiary.dto.*;
import com.rest.moodiary.entity.User;
import com.rest.moodiary.jwt.JwtFilter;
import com.rest.moodiary.jwt.TokenProvider;
import com.rest.moodiary.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입
    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<SignupDto> signup(@Valid @RequestBody UserDto userDto) throws DuplicateMemberException {

        Long id = userService.signup(userDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(id)
                .toUri();

        User user = userService.findOne(id);
        SignupDto signupDto = new SignupDto(user.getUserName(), user.getJoinDate());

        return ResponseEntity.created(uri).body(signupDto);
    }

    // 로그인
    @ApiOperation(value = "로그인", notes = "로그인 정보 확인 후 토큰 반환")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    // 로그인한 유저의 정보 검색(SecurityContext 에 저장된 username 의 정보 확인)
    @ApiOperation(value = "로그인된 회원 정보", notes = "로그인된 회원정보를 반환")
    @GetMapping("/login")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<LoginUserDto> getMyUserInfo(HttpServletRequest request) throws NotFoundException {

        User user = userService.getMyUserWithAuthorities().get();

        return ResponseEntity.ok(new LoginUserDto(user));
    }

    // 전체 회원 조회
    @ApiOperation(value = "전체 회원 조회", notes = "관리자 권한(ROLE_ADMIN) 필요")
    @GetMapping("/users")
    public Result findAll(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit)
    {
        // 로그인 유저 확인
        User user = userService.getMyUserWithAuthorities().get();

        PageDto page = new PageDto(offset, limit);

        List<User> findList = userService.findAll(page);

        List<UserListDto> UserList = findList.stream()
                .map(u -> new UserListDto(u))
                .collect(Collectors.toList());

        return new Result(UserList);
    }

    // 회원 수정
    @ApiOperation(value = "회원 수정(회원이름(ID), 비밀번호 수정)", notes = "관리자 권한(ROLE_ADMIN) 필요")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserIdDto> updateUser(
            @PathVariable("id") Long userId,
            @RequestBody @Valid UserDto userUpdateDto){

        // 로그인 유저 확인
        User loginUser = userService.getMyUserWithAuthorities().get();

        User updateUser = userService.updateUser(userId, userUpdateDto);

        return ResponseEntity.ok().body(new UserIdDto(updateUser.getUserId()));
    }

    // 회원 탈퇴
    @ApiOperation(value = "회원 탈퇴", notes = "관리자 권한(ROLE_ADMIN) 필요")
    @PutMapping("/users/withdrawal/{id}")
    public ResponseEntity<UserIdDto> withdrawal(@PathVariable("id") Long userId){

        // 로그인 유저 확인
        User loginUser = userService.getMyUserWithAuthorities().get();

        User withdrawal = userService.withdrawal(userId);

        return ResponseEntity.ok().body(new UserIdDto(withdrawal.getUserId()));
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "회원의 아이디를 반환해주는 DTO")
    static class UserIdDto {

        private Long userId;
    }
}
