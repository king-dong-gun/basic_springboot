package com.come1997.backboard.restcontroller;

import com.come1997.backboard.dto.BoardDto;
import com.come1997.backboard.dto.ReplyDto;
import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Category;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.service.CategoryService;
import com.come1997.backboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
@Log4j2
public class RestBoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final CategoryService categoryService;

    @GetMapping("/list/{category}")
    @ResponseBody
    public List<BoardDto> list(
            @PathVariable(value = "category") String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String keyword) {

        Category cate = this.categoryService.getCategory(category);
        Page<Board> paging = this.boardService.getList(page, keyword, cate);

        //
        List<BoardDto> result = paging.stream().map(board -> {
            // replies 객체 선언
            List<ReplyDto> replies = board.getReplyList().stream().map(reply -> ReplyDto.builder()
                    .rno(reply.getRno())
                    .content(reply.getContent())
                    .writer(reply.getWriter().getUsername())
                    .createDate(reply.getCreateDate())
                    .build()).collect(Collectors.toList());

            // BoardDto 리턴
            return BoardDto.builder()
                    .bno(board.getBno())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createDate(board.getCreateDate())
                    .modifyDate(board.getModifyDate())
                    .writer(board.getWriter().getUsername())
                    .hit(board.getHit())
                    .replyDtoList(replies)  // replies 리턴
                    .build();
            // 리스트를 수집하여 result 리스트에 저장
        }).collect(Collectors.toList());

        log.info(String.format("==========리스트에서 넘긴 게시글 수 %s", result.size()));

        return result;
    }
}
