package com.come1997.backboard.controller;

import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.service.MemberService;
import com.come1997.backboard.validation.BoardForm;
import com.come1997.backboard.validation.ReplyForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@RequiredArgsConstructor
@RequestMapping("/board")   // Restful URL은 /board로 시작
@Controller
@Log4j2
// @RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

//    // 주로 데이터를 조회하거나, 리소스를 가져오는 데 사용할 때 get
    // @RequestMapping("/list", method = RequestMethod.GET) -> 아래와 동일 기능
//    @GetMapping("/")
//    // Model: controller에 있는 객체를 view로 보내주는 역할을 하는 객체
//    public String requestMethodName(Model model, @RequestParam(value = "page", defaultValue = "0")int page) {
////        List<Board>boardList = this.boardService.getList();
////        model.addAttribute("boardList", boardList); // thymeleaf, mustache, jsp등을 view로 보내는 기능!
//        Page<Board> paging = this.boardService.getList(page);   // 페이징된 Board를 view로 전달!!
//        model.addAttribute("paging", paging);
//
//        return "board/list";    // templates/board/list.html을 랜더링해서 리턴해라!
//    }

    // 게시글 상세페이지
    // ReplyForm을 넘겨줘야함!
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno, ReplyForm replyForm, HttpServletRequest request) {
        String prevUrl = request.getHeader("referer");  // 이전 페이지 변수에 담기
        log.info(String.format("➡️현재 이전 페이지 : %s", prevUrl));
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        model.addAttribute("prevUrl", prevUrl); // 이전 페이지 URL 뷰에 전달
        return "board/detail";
    }


    // 게시글 만들기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board/create";
    }

    // 만든 게시글 저장
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/create";  // 현재 html에 그대로 머무르기
        }

        Member writer = this.memberService.getMember(principal.getName());
        this.boardService.setBoard(boardForm.getTitle(), boardForm.getContent(), writer);
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{bno}")
    public String modify(@PathVariable("bno") Long bno, BoardForm boardForm, Principal principal) {
        Board board = this.boardService.getBoard(bno);

        // 현재 로그인한 사용자와 게시글의 작성자가 일치하지 않으면 권한 없음 오류 반환
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다 !");
        }

        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        return "board/create";  // 수정 화면으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{bno}")
    public String modify(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal, @PathVariable("bno") Long bno) {
        if (bindingResult.hasErrors()) {
            return "board/create";  // 현재 html에 그대로 머무르기
        }
        Board board = this.boardService.getBoard(bno);
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다 !");
        }
        this.boardService.modBoard(board, boardForm.getTitle(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", bno);
    }

    // 삭제 처리
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{bno}")
    public String delete(@PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoard(bno);
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다 !");
        }

        this.boardService.remBoard(board);  // 삭제
        return "redirect:/";
    }

    // 검색기능 추가 -> /list 06.24 변경
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0")int page,
            @RequestParam(value = "kw", defaultValue = "") String keyword) {

        Page<Board> paging = this.boardService.getList(page, keyword);  // 검색 추가
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);

        return "board/list";
    }
}
