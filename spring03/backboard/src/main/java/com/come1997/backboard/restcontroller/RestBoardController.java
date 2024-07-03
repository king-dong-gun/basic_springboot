package com.come1997.backboard.restcontroller;

import com.come1997.backboard.dto.BoardDto;
import com.come1997.backboard.dto.Header;
import com.come1997.backboard.dto.PagingDto;
import com.come1997.backboard.dto.ReplyDto;
import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Category;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.service.CategoryService;
import com.come1997.backboard.service.MemberService;
import com.come1997.backboard.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
@Log4j2
public class RestBoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ReplyService replyService;

    @GetMapping("/list/{category}")
    @ResponseBody
    public Header<List<BoardDto>> list(
            @PathVariable(value = "category") String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String keyword) {

        Category cate = this.categoryService.getCategory(category);
        Page<Board> paging = this.boardService.getList(page, keyword, cate);

        PagingDto pagingDto = new PagingDto(10, paging.getTotalPages(), paging.getTotalElements(), 10);
        AtomicLong currentNum = new AtomicLong(paging.getTotalElements() - (paging.getNumber() * 10));

        List<BoardDto> result = paging.stream().map(board -> {
            // replies 객체 선언
            List<ReplyDto> replies = board.getReplyList().stream().map(reply -> ReplyDto.builder()
                    .rno(reply.getRno())
                    .content(reply.getContent())
                    .writer(reply.getWriter().getUsername())
                    .createDate(reply.getCreateDate())
                    .build()).collect(Collectors.toList());

            // BoardDto 리턴
            BoardDto boardDto = BoardDto.builder()
                    .bno(currentNum.getAndDecrement())  // 현재 번호를 사용하고 감소시킴
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createDate(board.getCreateDate())
                    .modifyDate(board.getModifyDate())
                    .writer(board.getWriter().getUsername())
                    .hit(board.getHit())
                    .replyDtoList(replies)  // replies 리턴
                    .build();
            // 리스트를 수집하여 result 리스트에 저장
            return boardDto;
        }).collect(Collectors.toList());

        log.info(String.format("==========리스트에서 넘긴 게시글 수 %s", result.size()));

        Header<List<BoardDto>> header = Header.OK(result, pagingDto);
        return header;
    }

    @GetMapping("/detail/{bno}")
    @ResponseBody
    public BoardDto detail(@PathVariable("bno") Long bno, HttpServletRequest request) {

        String prevUrl = request.getHeader("referer");  // 이전페이지 변수에 담기
        log.info(String.format("▶▶▶▶▶ 현재 이전 페이지 : %s", prevUrl));
        Board board = this.boardService.hitBoard(bno);  // 조회수를 증가하고 리턴시킴

        // replies 객체 선언
        List<ReplyDto> replies = board.getReplyList().stream().map(reply -> ReplyDto.builder()
                .rno(reply.getRno())
                .content(reply.getContent())
                .writer(reply.getWriter().getUsername())  // 댓글 작성자 정보 수정
                .createDate(reply.getCreateDate())
                .modifyDate(reply.getModifyDate())
                .build()).collect(Collectors.toList());

        // BoardDto 리턴
        BoardDto boardDto = BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .createDate(board.getCreateDate())
                .modifyDate(board.getModifyDate())
                .writer(board.getWriter().getUsername())
                .hit(board.getHit())
                .replyDtoList(replies)  // replies 리턴
                .build();

        return boardDto;
    }
}
