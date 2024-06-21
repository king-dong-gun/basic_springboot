package com.come1997.backboard.controller;

import com.come1997.backboard.service.MemberService;
import com.come1997.backboard.validation.MemberForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }


    // 회원가입
    @GetMapping("/registar")
    public String registar(MemberForm memberForm) {

        return "member/registar";   // member/registar로 랜더링
    }

    @PostMapping("/registar")
    public String registar(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/registar";
        }
        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다!");
            return "member/registar";
        }
        // 이미 가입된 회원 중복금지 처리
        try {
            this.memberService.setMember(memberForm.getUsername(), memberForm.getEmail(), memberForm.getPassword1());
        }   catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("registerFailed", "이미 가입된 회원입니다!");
            return "member/registar";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("registerFailed", e.getMessage());
            return "member/registar";
        }

        return "redirect:/";
    }
}
