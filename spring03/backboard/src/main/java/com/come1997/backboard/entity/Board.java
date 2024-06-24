package com.come1997.backboard.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

// 게시판 보드 테이블 엔티티
@Builder
@Entity // 테이블화
@Data // 게터 세터 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본생성자 자동생성
@AllArgsConstructor // 맴버변수 모두를 파라미터로 가지는 생성자 자동생성
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // 나는 나중에 오라클로 바꿀거임!
    private Long bno; // PK

    @Column(name = "title", length = 250)
    private String title; // 글제목

    @Column(name = "content", length = 4000)
    private String content; // 글내용

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate; // 글 생성일

    // 사용자가 여러개의 게시글을 작성할 수 있다, 다대일
    @ManyToOne
    private Member writer;

    @LastModifiedDate
    @Column(name = "modifyDate")
    private LocalDateTime modifyDate;   // 24.06.24 수정일 추가


    // 중요, RelationShip 일대다
    @OrderBy("createDate ASC") // 댓글을 작성일 기준으로 오름차순 정렬
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replyList;

}
