package com.come1997.backboard.repository;

import com.come1997.backboard.entity.Board;
import com.come1997.backboard.service.BoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BoardRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(BoardRepositoryTest.class);

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("Board 저장 테스트")
    public void save() throws Exception {
        //given
        Board book = Board.builder().title("검찰총장").content("윤석열").createDate(LocalDateTime.now()).build();
        //when
        Board bookPS = boardRepository.save(book);

//        log.info("book.id = {}", book.getBno());
//        log.info("book.title = {}", book.getTitle());
//        log.info("book.content = {}", book.getContent());
//
//        log.info("bookPS.id = {}", bookPS.getBno());
//        log.info("bookPS.title = {}", bookPS.getTitle());
//        log.info("bookPS.content = {}", bookPS.getContent());
        //then
        assertThat(bookPS.getContent()).isEqualTo(book.getContent());
        assertThat(bookPS.getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    @DisplayName("Board 한건 조회 테스트")
    public void select() throws Exception {
        //given
        Board bookPS = boardRepository.saveAndFlush(Board.builder().title("첫번 째 테스트").content("어쩌라구").createDate(LocalDateTime.now()).build());

        //when
        Board savedBoard = boardRepository.findById(bookPS.getBno()).orElseThrow(() -> {
            throw new RuntimeException("NULL");
        });

        //then
        assertThat(savedBoard.getTitle()).isEqualTo(bookPS.getTitle());
    }

    @Test
    @DisplayName("Board 전제 조회 테스트")
    public void selectAll() throws Exception {
        //given

        //when
        List<Board> boards = boardRepository.findAll();

        //then
        assertThat(boards.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Board 수정 테스트")
    public void update() throws Exception {
        //given
        Board book = Board.builder().title("첫번 째 테스트").content("어쩌라구").createDate(LocalDateTime.now()).build();
        Board bookPS = boardRepository.save(book);

        //when
        bookPS.setContent("수정했어요");
        boardRepository.saveAndFlush(bookPS);

        //then
        Board findBoard = boardRepository.findById(bookPS.getBno()).orElseThrow(() -> {
            throw new RuntimeException("Null 객체");
        });

        assertThat(findBoard.getContent()).isEqualTo("수정했어요");
    }

    @Test
    @DisplayName("Board 삭제 테스트")
    public void delete() throws Exception {
        //given
        Board book = Board.builder().title("첫번 째 테스트").content("어쩌라구").createDate(LocalDateTime.now()).build();
        boardRepository.saveAndFlush(book);

        //when
        boardRepository.deleteById(book.getBno());
        Optional<Board> findBoard = boardRepository.findById(book.getBno());

        //then
        Assertions.assertFalse(findBoard.isPresent());
    }

    @Test
    @DisplayName("데이터 300개 불러오기 테스트")
    public void paging() throws Exception {
        //given
        for (int i = 0; i < 300; i++) {
            boardService.setBoard((String.format("테스트 데이터 - %03d", (i + 1))), "별내용 업습니다.");
        }
        //when

        //then
    }
}