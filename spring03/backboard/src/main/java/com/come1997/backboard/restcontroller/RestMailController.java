package com.come1997.backboard.restcontroller;

import com.come1997.backboard.service.MailService;
import com.come1997.backboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
@Log4j2
public class RestMailController {

    private final MailService mailService;

    @PostMapping("/test-mail")
    @ResponseBody
    public ResponseEntity testEmail() {
        String to = "a01021290607@gmail.com";
        String subject = "전송 테스트";
        String message = "테스트 메일 전송했다. 받아라";

        mailService.sendMail(to, subject, message);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
