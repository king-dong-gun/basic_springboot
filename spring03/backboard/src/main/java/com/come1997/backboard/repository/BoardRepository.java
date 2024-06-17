package com.come1997.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.come1997.backboard.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
}
