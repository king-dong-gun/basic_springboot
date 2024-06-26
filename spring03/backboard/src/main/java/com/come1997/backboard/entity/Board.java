package com.come1997.backboard.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//게시판 보드 테이블 엔티티
@Setter
@Getter
@Entity //테이블화
@Builder //객체 생성을 간략화
@NoArgsConstructor // 파라미터 없는 기본생성자 자동생성
@AllArgsConstructor // 멤버변수 모두를 파라미터로 가지는 생성자 자동생성
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //나는 나중에 Oracle로 바꿀거야!
    private Long bno;   // PK

    @Column(name="title", length = 250)
    private String title;   // 글제목

    @Column(name="content", length = 4000)
    private String content; // 글내용

    @CreatedDate
    @Column(name = "createDate", updatable = false)
    private LocalDateTime createDate;   //글생성일

    @LastModifiedDate
    @Column (name="modifyDate")
    private LocalDateTime modifyDate; // 24.06.24 수정일 추가

    // 24.06.26 조회수 추가
    private Integer hit = 0;

    // 사용자가 여러개의 게시글을 작성할 수 있다. 다대일 설정
    @ManyToOne
    private Member writer;

    //중요, RelationShip 일대다 설정
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replyList;

    //free, qna로 구분해서 글생성 가능
    @ManyToOne
    private Category category;


}