package com.come1997.backboard.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        this.boardRepository.save(board1);

        Board board2 = Board.builder()
                .title("두번째 테스트입니다.")
                .content("잠온다...")
                .createDate(LocalDateTime.now())
                .build();
        this.boardRepository.save(board2);

        log.info("Board Test Complete!!");
    }
}
