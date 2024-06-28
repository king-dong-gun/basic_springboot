package com.come1997.backboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.come1997.backboard.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);
}