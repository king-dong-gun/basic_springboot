package com.come1997.backboard.controller;


import com.come1997.backboard.entity.Board;
import com.come1997.backboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String requestMethodName(Model model) {  // Model: controller에 있는 객체를 view로 보내주는 역할을 하는 객체
        List<Board>boardList = this.boardService.getList();
        model.addAttribute("boardList", boardList); // thymeleaf, mustache, jsp등을 view로 보내는 기능!
        return "board/list";    // templates/board/list.html을 랜더링해서 리턴해라!
    }

    //
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno) throws Exception {
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        return "board/detail";
    }
}
