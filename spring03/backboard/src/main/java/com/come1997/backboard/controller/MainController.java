package com.come1997.backboard.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j2
public class MainController {
    @GetMapping("/hello")
    public String getHello() {
        log.info("getHello(); 실행");
        return "Hello";
    }

    @GetMapping("/")
    public String getMain() {
        return "redirect:/board/list";  // localhost:8080 -> localhost:8080/board/list로 번경

    }
}
