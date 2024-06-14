package com.come1997.spring02.service;

import java.util.List;

// 도메인 경로 import
import com.come1997.spring02.domain.Todo;

// Service는 인터페이스
public interface TodoService {
    public List<Todo> geTodos() throws Exception;

    public Todo geTodo(int tno) throws Exception;
}
