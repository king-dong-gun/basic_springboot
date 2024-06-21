package com.come1997.backboard.service;


import com.come1997.backboard.common.NotFoundException;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.repository.MemberRepository;
import com.come1997.backboard.security.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member setMember(String username, String email, String password) {
        // password는 암호화 때문에 포함하지 않는다
        Member member = Member.builder().username(username).email(email).regDate(LocalDateTime.now()).build();

        // BCryptPasswordEncoder는 매번 새롭게 객체를 생성한다
        // 이것보다는 Bean을 등록해서 쓰는게 유지보수를 위해 더 좋다

        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        member.setPassword(passwordEncoder.encode(password));   // 암호화한 값을 DB에 저장
        member.setRegDate(LocalDateTime.now());
        member.setMemberRole(MemberRole.USER);  // 일반 사용자 권한
        this.memberRepository.save(member);

        return member;
    }

    // 사용자를 가져오는 메서드
    public Member getMember(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);
        if (member.isPresent()) {
            return member.get();
        } else throw new NotFoundException("Member not found...");
    }
}
