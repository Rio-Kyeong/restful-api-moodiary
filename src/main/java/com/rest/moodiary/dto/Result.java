package com.rest.moodiary.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "List를 반환하면 배열 타입으로 나가서 유연성이 떨어지기 떄문에 컬렉션을 감싸 반환하는 제네릭 클래스")
public class Result<T> {
    private T list;
}
