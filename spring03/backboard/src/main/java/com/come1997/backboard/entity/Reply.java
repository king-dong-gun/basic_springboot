package com.come1997.backboard.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity // 테이블화
@Data // 게터 세터 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본생성자 자동생성
@AllArgsConstructor // 맴버변수 모두를 파라미터로 가지는 생성자 자동생성
public class Reply {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rno;

    @Column(name = "content", length = 1000)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate; // 글 생성일

    // 중요! ERD로 DB 설계를 하지 않고 엔티티클래스로 관계를 형성하려면 반드시 사용
    // RelationShip 다대일
    @ManyToOne
    private Board board;
}
