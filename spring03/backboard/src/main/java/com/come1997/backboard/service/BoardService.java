package com.come1997.backboard.service;

import com.come1997.backboard.common.NotFoundException;
import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.entity.Reply;
import com.come1997.backboard.repository.BoardRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // 페이징되는 리스트 메서드
    public Page<Board> getList(int page) {
        List<Sort.Order> sort = new ArrayList<>();
        sort.add(Sort.Order.desc("createDate"));
        // 페이지 사이즈를 동적으로도 변경가능
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sort));
        return this.boardRepository.findAll(pageable);
    }

    // 특정 bno의 값을 가진 객체의 데이터베이스 조회
    public Board getBoard(Long bno) {
        Optional<Board> board = boardRepository.findByBno(bno);
        if (board.isPresent()) {    // 데이터가 존재해야 실행된다.
            return board.get();
        } else {
            throw new NotFoundException("board not found");
        }
    }

    // 작성한 게시글 저장
    public void setBoard(String title, String content, Member writer) {
        // 빌더로 생성한 객체
        Board board = Board.builder().title(title).content(content).createDate(LocalDateTime.now()).build();
        board.setWriter(writer);
        this.boardRepository.save(board);
    }

    // 24.06.24 modBoard 추가
    // 수정 처리
    public void modBoard(Board board, String title, String content) {
        board.setTitle(title);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());   // 수정된 일자 변경
        this.boardRepository.save(board);
    }

    // 삭제처리
    public void remBoard(Board board) {
        this.boardRepository.delete(board);
    }

    // 검색기능
    public Specification<Board> searchBoard(String keyword) {
        return new Specification<Board>() {
            private static final long serialVersionUID = 1L;    // 필요한 값 추가작성

            @Override
            public Predicate toPredicate(Root<Board> board, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);   // 중복제거
                Join<Board, Reply> reply = board.join("replyList", JoinType.LEFT);
                return criteriaBuilder.or(
                        criteriaBuilder.like(board.get("title"), "%" + keyword + "%"), // 게시글 제목에서 검색
                        criteriaBuilder.like(board.get("content"), "%" + keyword + "%"), // 게시글 내용에서 검색
                        criteriaBuilder.like(reply.get("content"), "%" + keyword + "%") // 댓글 내용에서 검색
                );
            }
        };
    }

    // 검색 기능 추가
    public Page<Board> getList(int page, String keyword) {
        List<Sort.Order> sort = new ArrayList<>();
        sort.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sort));

        Specification<Board> spec = searchBoard(keyword);
        return this.boardRepository.findAll(spec, pageable);
    }
}
