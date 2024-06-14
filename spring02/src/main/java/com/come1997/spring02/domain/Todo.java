package com.come1997.spring02.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

// Data를 쓰면 lombok이 자동 생성해줌
@Data
@NoArgsConstructor
// @ToString
// @Getter
// @Setter
public class Todo {
    private Integer tno;
    private String title; // 제목
    private LocalDateTime duDate; // 일자
    private String writer; // 사람
    private Integer isDone; // 유무
}
