package com.come1997.backboard.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    // 중요, RelationShip 일대다
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replyList;

}
