package com.come1997.backboard.controller;

import com.come1997.backboard.entity.Board;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.service.ReplyService;
import com.come1997.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;

    // 데이터를 서버로 전송하거나, 상태나 리소스를 변경할 때 post
    @PostMapping("/create/{bno}")
    public String create(Model model, @PathVariable("bno") Long bno, @Valid ReplyForm replyForm, BindingResult bindingResult) throws Exception {

        Board board = this.boardService.getBoard(bno);
        if(bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board/detail";
        }
        this.replyService.setReply(board, replyForm.getContent());
        log.info("ReplyController 댓글저장 완료!");
        return String.format("redirect:/board/detail/%s", bno);
    }
}
