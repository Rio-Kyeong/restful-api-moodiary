package com.rest.moodiary.service;

import com.rest.moodiary.dto.PageDto;
import com.rest.moodiary.dto.UserDto;
import com.rest.moodiary.entity.Authority;
import com.rest.moodiary.entity.User;
import com.rest.moodiary.exception.UnauthorizedException;
import com.rest.moodiary.exception.UserException;
import com.rest.moodiary.exception.NotFoundException;
import com.rest.moodiary.repository.UserRepository;
import com.rest.moodiary.util.SecurityUtil;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public Long signup(@Valid UserDto userDto) {

        // 중복확인
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUserName()).orElse(null) != null) {
            throw new UserException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .userName(userDto.getUserName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .joinDate(LocalDate.now())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    // 아이디(PK)로 개별 회원 조회
    @Transactional(readOnly = true)
    public User findOne(Long id){
        User user = userRepository.findOne(id);

        if(user == null){
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }

        return user;
    }

    //회원 수정
    @Transactional
    public User updateUser(Long userId, UserDto userUpdateDto){

        User findUser = userRepository.findOne(userId);

        findUser.updateUser(userUpdateDto.getUserName(), passwordEncoder.encode(userUpdateDto.getPassword()));

        return findUser;
    }

    //회원 탈퇴
    @Transactional
    public User withdrawal(Long userId){

        User findUser = userRepository.findOne(userId);

        findUser.withdrawal(false);

        return findUser;
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<User> findAll(PageDto page){
        List<User> findList = userRepository.findAll(page);

        if(findList.isEmpty()){
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }

        return findList;
    }

    // 로그인
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {

        return (UserDetails) userRepository.findOneWithAuthoritiesByUsername(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new NotFoundException(username + "님은 가입되지 않은 회원입니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {

        if (!user.isActivated()) {
            throw new NotFoundException("회원 탈퇴한 계정입니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                grantedAuthorities);
    }

    // 로그인한 유저의 정보 검색(SecurityContext 에 저장된 username 의 정보 확인)
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);

        if(user.isEmpty()){
            throw new UnauthorizedException("로그인 시도 후 다시 시도해주세요.");
        }
        return user;
    }
}