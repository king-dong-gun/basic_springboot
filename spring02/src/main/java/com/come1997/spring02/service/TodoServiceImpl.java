package com.come1997.spring02.service;

import org.apache.ibatis.annotations.Delete.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.come1997.spring02.mapper.TodoMapper;

public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoMapper todoMapper;

    @Override
    public List<Todo> getTodos() throws Exception {
        return todoMapper.selectTodos();
    }

    @Override
    public Todo geTodo(int tno) throws Exception {
        return todoMapper.selectTodo(tno);
    }

}
