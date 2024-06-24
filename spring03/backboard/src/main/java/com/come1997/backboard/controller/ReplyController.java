package com.come1997.backboard.controller;

import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.entity.Reply;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.service.MemberService;
import com.come1997.backboard.service.ReplyService;
import com.come1997.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;
    private final MemberService memberService;  // 작성자를 입력하기 위해서 추가

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{bno}")
    public String create(Model model, @PathVariable("bno") Long bno, @Valid ReplyForm replyForm, BindingResult bindingResult, Principal principal) throws Exception {

        Board board = this.boardService.getBoard(bno);
        Member writer = this.memberService.getMember(principal.getName());  // 현재 로그인중인 사용자의 id를 가져온다

        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board/detail";
        }
        Reply reply = this.replyService.setReply(board, replyForm.getContent(), writer);
        log.info("ReplyController 댓글저장 완료!");
        return String.format("redirect:/board/detail/%s#reply_%s", bno, reply.getRno());
    }

    // 댓글 작성자가 ID와 맞으면 게시글 수정페이지로
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{rno}")
    public String modify(ReplyForm replyForm, @PathVariable("rno") Long rno, Principal principal) {
        Reply reply = this.replyService.getReply(rno);

        if (!reply.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다 !");
        }
        replyForm.setContent(reply.getContent());
        return "reply/modify";
    }
    // 댓글 작성자가 ID와 맞으면 수정페이지 -> 게시글 상세 페이지
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{rno}")
    public String modify(@Valid ReplyForm replyForm, @PathVariable("rno") Long rno, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "reply/modify";
        }
        Reply reply = this.replyService.getReply(rno);
        if (!reply.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다 !");
        }
        this.replyService.modReply(reply, replyForm.getContent());
        return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getBno(), reply.getRno());  // 게시글 ID를 사용하여 리디렉션
    }
    // 게시글 작성자가 ID와 맞으면 삭제를 눌렀을때 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{rno}")
    public String delete (@PathVariable("rno") Long rno, Principal principal) {
        Reply reply = this.replyService.getReply(rno);
        if (!reply.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다 !");
        }

        this.replyService.remReply(reply);
        return String.format("redirect:/board/detail/%s", reply.getBoard().getBno());  // 삭제 후 게시글 상세 페이지로 리디렉션
    }
}
