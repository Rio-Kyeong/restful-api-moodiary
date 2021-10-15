package com.rest.moodiary.controller;

import com.rest.moodiary.dto.*;
import com.rest.moodiary.entity.User;
import com.rest.moodiary.jwt.JwtFilter;
import com.rest.moodiary.jwt.TokenProvider;
import com.rest.moodiary.service.UserService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
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
}
