package com.come1997.spring02.controller;

import org.springframework.stereotype.Controller;

import com.come1997.spring02.domain.Todo;
import com.come1997.spring02.service.TodoService;

// import -> javax, jakarta 정확히 확인! (자바 버전에 따라 다름)
import jakarta.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TodoController {

    @Resource
    TodoService todoService;

    @GetMapping("/todos")
    public String getTodos(Model model) throws Exception {
        model.addAttribute("todos", todoService.getTodos());
        return "todolist";
    }

    @GetMapping("/todo/{tno}")
    @ResponseBody
    public Todo getTodo(@PathVariable int tno) throws Exception {
        return todoService.getTodo(tno);
    }
}
