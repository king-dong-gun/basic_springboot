package com.come1997.backboard.controller;

import com.come1997.backboard.entity.Board;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.validation.BoardForm;
import com.come1997.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")   // Restful URL은 /board로 시작
@Controller
// @RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 주로 데이터를 조회하거나, 리소스를 가져오는 데 사용할 때 get
    // @RequestMapping("/list", method = RequestMethod.GET) -> 아래와 동일 기능
    @GetMapping("/list")
    // Model: controller에 있는 객체를 view로 보내주는 역할을 하는 객체
    public String requestMethodName(Model model, @RequestParam(value = "page", defaultValue = "0")int page) {
//        List<Board>boardList = this.boardService.getList();
//        model.addAttribute("boardList", boardList); // thymeleaf, mustache, jsp등을 view로 보내는 기능!
        Page<Board> paging = this.boardService.getList(page);   // 페이징된 Board를 view로 전달!!
        model.addAttribute("paging", paging);

        return "board/list";    // templates/board/list.html을 랜더링해서 리턴해라!
    }

    // 게시글 상세페이지
    // ReplyForm을 넘겨줘야함!
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno, ReplyForm replyForm) throws Exception {
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        return "board/detail";
    }

    // 게시글 만들기
    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board/create";
    }

    // 만든 게시글 저장
    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/create";  // 현재 html에 그대로 머무르기
        }

//        this.boardService.setBoard(title, content);
        this.boardService.setBoard(boardForm.getTitle(), boardForm.getContent());
        return "redirect:/board/list";
    }
}
