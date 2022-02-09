package com.rest.moodiary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 상세 정보 Entity")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "사용자 고유번호")
    private Long userId;

    @Column(name = "user_name")
    @ApiModelProperty(notes = "사용자 이름(아이디로 사용)")
    private String userName;

    @JsonIgnore
    @Column(name = "user_password")
    @ApiModelProperty(notes = "사용자 비밀번호")
    private String password;

    @Column(name = "user_date")
    @ApiModelProperty(notes = "가입날짜")
    private LocalDate joinDate;

    @JsonIgnore
    @Column(name = "activated")
    @ApiModelProperty(notes = "활성화 여부(탈퇴회원은 false)")
    private boolean activated;

    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    @ApiModelProperty(notes = "사용자 권한")
    private Set<Authority> authorities;

    // 회원 수정
    public void updateUser(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    // 회원 탈퇴
    public void withdrawal(boolean activated){
        this.activated = activated;
    }
}