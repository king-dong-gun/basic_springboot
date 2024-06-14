package com.come1997.spring02.mapper;

import java.util.List;
// IBatis라서 import, 큰문제는 없음
import org.apache.ibatis.annotations.Mapper;

import com.come1997.spring02.domain.Todo;

@Mapper
public interface TodoMapper {
    // 데이터베이스와의 상호 작용 중 발생할 수 있는 모든 예외를 던질 수 있다
    // 객체 반환
    List<Todo> selectTodos() throws Exception;

    Todo selectTodo(int tno) throws Exception;
}
