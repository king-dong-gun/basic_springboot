package com.come1997.backboard.service;


import com.come1997.backboard.entity.Member;
import com.come1997.backboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member setMember(String username, String email, String password) {
        // password는 암호화 때문에 포함하지 않는다
        Member member = Member.builder().username(username).email(email).build();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 암호화한 값을 DB에 저장
        member.setPassword(encoder.encode(password));
        this.memberRepository.save(member);

        return member;
    }
}
