package com.come1997.backboard.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Category;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.service.BoardService;
import com.come1997.backboard.service.CategoryService;
import com.come1997.backboard.service.MemberService;
import com.come1997.backboard.validation.BoardForm;
import com.come1997.backboard.validation.ReplyForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@RequestMapping("/board") // Restful URL은 /board로 시작
@Controller
@Log4j2
public class BoardController {

    private final BoardService boardService; // 중간 연결책
    private final MemberService memberService; // 사용자 정보
    private final CategoryService categoryService; // 카테고리 사용.

//    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String keyword) {
        Page<Board> paging = this.boardService.getList(page, keyword);  // 검색추가
        model.addAttribute("paging", paging);
        model.addAttribute("kw", keyword);

        return "board/list";
    }

    @GetMapping("/list/{category}")
    public String list(Model model,
                       @PathVariable(value = "category") String category,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String keyword) {

        Category cate = this.categoryService.getCategory(category); // cate는 Category 객체 변수 사용 X
        Page<Board> paging = this.boardService.getList(page, keyword, cate);  // 검색 및 카테고리 추가
        model.addAttribute("paging", paging);
        model.addAttribute("kw", keyword);
        model.addAttribute("category", category);

        return "board/list";
    }

    @GetMapping("/detail/{bno}")
    public String detail(Model model,
                         @PathVariable("bno") Long bno, ReplyForm replyForm, HttpServletRequest request) {

        String prevUrl = request.getHeader("referer");  // 이전페이지 변수에 담기
        log.info(String.format("▶▶▶▶▶ 현재 이전 페이지 : %s", prevUrl));
        Board board = this.boardService.hitBoard(bno);  // 조회수를 증가하고 리턴시킴
        model.addAttribute("board", board);
        model.addAttribute("prevUrl", prevUrl); // 이전 페이지 URL 뷰에 전달
        return "board/detail";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 시만 작성 가능
    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board/create";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 시만 작성 가능
    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm,
                         BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/create"; // 현재 html에 그대로 머무르기.
        }

        Member writer = this.memberService.getMember(principal.getName()); // 현재 로그인 사용자 아이디
        this.boardService.setBoard(boardForm.getTitle(), boardForm.getContent(), writer);
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 시만 작성 가능
    @GetMapping("/create/{category}")
    public String create(Model model,
                         @PathVariable("category") String category,
                         BoardForm boardForm) {
        model.addAttribute("category", category);
        return "board/create";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 시만 작성 가능
    @PostMapping("/create/{category}")
    public String create(Model model,
                         @PathVariable("category") String category,
                         @Valid BoardForm boardForm,
                         BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            return "board/create"; // 현재 html에 그대로 머무르기.
        }

        Member writer = this.memberService.getMember(principal.getName()); // 현재 로그인 사용자 아이디
        Category cate = this.categoryService.getCategory(category);
        this.boardService.setBoard(boardForm.getTitle(), boardForm.getContent(), writer, cate);
        return String.format("redirect:/board/list/%s", category);
    }

    @PreAuthorize("isAuthenticated()") // 로그인 시만 작성 가능
    @GetMapping("/modify/{bno}")
    public String modify(BoardForm boardForm, @PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoard(bno); // 기본 게시글 가져옴

        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        return "board/create"; // 게시글 등록하는 화면을 수정할 때 그대로 사용
    }

    @PreAuthorize("isAuthenticated()") // 로그인 시만 작성 가능
    @PostMapping("/modify/{bno}")
    public String modify(@Valid BoardForm boardForm,
                         BindingResult bindingResult,
                         Principal principal,
                         @PathVariable("bno") Long bno) {
        if (bindingResult.hasErrors()) {
            return "board/create"; // 현재 html에 그대로 머무르기.
        }

        Board board = this.boardService.getBoard(bno);
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.boardService.modBoard(board, boardForm.getTitle(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", bno);
    }

    @PreAuthorize("isAuthenticated()") // 로그인 시만 작성 가능
    @GetMapping("/delete/{bno}")
    public String delete(@PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoard(bno);
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.boardService.remBoard(board); // 삭제
        return "redirect:/";
    }
}
