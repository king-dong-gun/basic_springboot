package com.come1997.backboard.controller;

import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.service.MemberService;
import com.come1997.backboard.service.ReplyService;
import com.come1997.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;
    private final MemberService memberService;  // 작성자를 입력하기 위해서 추가

    // 데이터를 서버로 전송하거나, 상태나 리소스를 변경할 때 post
    // Principal 어노테이션: 로그인한 사용자명(Member)객체를  알 수 있다.
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{bno}")
    public String create(Model model, @PathVariable("bno") Long bno, @Valid ReplyForm replyForm, BindingResult bindingResult, Principal principal) throws Exception {

        Board board = this.boardService.getBoard(bno);
        Member writer = this.memberService.getMember(principal.getName());  // 현재 로그인중인 사용자의 id를 가져온다

        if(bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board/detail";
        }
        this.replyService.setReply(board, replyForm.getContent(), writer);
        log.info("ReplyController 댓글저장 완료!");
        return String.format("redirect:/board/detail/%s", bno);
    }
}
