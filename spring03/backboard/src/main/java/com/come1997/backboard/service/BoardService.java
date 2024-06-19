package com.come1997.backboard.service;


import com.come1997.backboard.entity.Board;
import com.come1997.backboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 메서드 작성
    // 전체 정보 가져오기
    public List<Board> getList() {
        return boardRepository.findAll();
    }

    // 특정 bno의 값을 가진 객체의 데이터베이스 조회
    public Board getBoard(Long bno) throws Exception{
        Optional<Board> board = boardRepository.findById(bno);
        if (board.isPresent()) {    // 데이터가 존재해야 실행된다.
            return board.get();
        } else {
            throw new Exception("board not found");
        }
    }

    // 작성한 게시글 저장
    public void setBoard(String title, String content) {
        // 빌더로 생성한 객체
        Board board = Board.builder().title(title).content(content).createDate(LocalDateTime.now()).build();

        this.boardRepository.save(board);
    }
}
