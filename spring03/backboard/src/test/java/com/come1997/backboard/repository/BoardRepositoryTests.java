package com.come1997.backboard.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.come1997.backboard.entity.Board;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void testInsertBoard() {
        Board board1 = new Board();
        board1.setTitle("첫번째 테스트입니다.");
        board1.setContent("귀찮네 진짜");
        board1.setCreateDate(LocalDateTime.now());
        Board boardPS = boardRepository.save(board1);

        Assertions.assertThat(boardPS.getBno()).isEqualTo(board1.getBno());
        log.info("Board Test Complete!!");

    }

    @Test
    void testSelectBoard() {
        List<Board> all = this.boardRepository.findAll(); // select * from board
        assertEquals(0, all.size());

        // Board board = all.get(0); // 게시글중 가장 첫번째 값
        // assertEquals(1, board.getBno()); // 첫번째 게시글의 PK값이 1인지 확인
    }
}
