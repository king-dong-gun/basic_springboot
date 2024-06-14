package com.come1997.spring02.controller;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.come1997.spring02.service.TodoService;

// import -> javax, jakarta 정확히 확인! (자바 버전에 따라 다름)
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {
    @Resource
    TodoService todoService;

    // localhost:8091/todos로 요청하면 실행되는 메서드
    // @Responsebody
    @GetMapping("/todos")
    public List<Todo> getMethodName(@RequestParam String param) throws Exception {
        List<Todo> allTodos = todoService.geTodos();
        return allTodos;
    }

}
