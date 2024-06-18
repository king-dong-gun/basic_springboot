package com.come1997.backboard.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.come1997.backboard.entity.Board;


@SpringBootTest
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
//        log.info("Board Test Complete!!");
    }

    @Test
    void testSelectBoard() {
        List<Board> all = this.boardRepository.findAll(); // select * from board
        assertEquals(4, all.size());

        // Board board = all.get(0); // 게시글중 가장 첫번째 값
        // assertEquals(1, board.getBno()); // 첫번째 게시글의 PK값이 1인지 확인
    }

    @Test
    void testUpdateBoard() {
        Optional<Board> board = this.boardRepository.findById(52L);  // Long 값은 뒤에 L 추가
        assertTrue(board.isPresent());  // bno가 1번인 게시글이 객체가 넘어왔는지 확인
        Board board1 = board.get();
        board1.setContent("테스트로 수정합니다.");
        this.boardRepository.save(board1);  // save() id가 없으면 INSERT, 있으면 UPDATE 쿼리 자동실행
        System.out.println("수정완료!!");
    }

    @Test
    void testDeleteBoard() {
        Optional<Board> board = this.boardRepository.findById(52L);
        assertTrue(board.isPresent());
        Board board1 = board.get();
        this.boardRepository.delete(board1);
        System.out.println("삭제완료!!");
    }
}
