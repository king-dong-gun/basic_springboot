package com.come1997.backboard.dto;

import com.come1997.backboard.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long num;   // 24.07.03 추가
    private long bno;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Integer hit;
    private String writer;

    private List<ReplyDto> replyDtoList;
}

