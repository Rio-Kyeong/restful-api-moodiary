package com.rest.moodiary.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자의 권한 정보 Entity")
public class Authority {

    @Id
    @Column(name = "authority_name")
    @ApiModelProperty(notes = "사용자 권한")
    private String authorityName;
}
