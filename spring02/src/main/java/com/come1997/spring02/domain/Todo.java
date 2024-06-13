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
    private String title;
    private LocalDateTime duDate;
    private String writer;
    private Integer isDone;
}
