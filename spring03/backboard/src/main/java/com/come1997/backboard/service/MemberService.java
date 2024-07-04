package com.come1997.backboard.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.come1997.backboard.common.NotFoundException;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.repository.MemberRepository;
import com.come1997.backboard.security.MemberRole;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 새로 사용자 생성
    public Member setMember(String username, String email, String password) {
        Member member = Member.builder().username(username).email(email).regDate(LocalDateTime.now()).build();

        // ... 처리되는 일이 많아서 1~2초 걸리면
        // BCryptPasswordEncoder 매번 새롭게 객체를 생성한다
        // 이것보다는 Bean 등록해놓고 쓰는게 유지보수를 위해서 더 좋음
        // BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(password));    // 암호화한 값을 DB에 저장
        member.setRegDate(LocalDateTime.now());
        member.setMemberRole(MemberRole.USER);    // 일반사용자 권한
        this.memberRepository.save(member);

        return member;
    }

    // 기존 사용자 비밀번호 초기화
    public void setMember(Member member) {
        member.setPassword(passwordEncoder.encode((member.getPassword()))); // Bcrypt 암호화로 업데이트
        this.memberRepository.save(member);     // 업데이트
    }

    // 사용자를 가져오는 메서드
    public Member getMember(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new NotFoundException("Member not found");
        }
    }

    // 24.06.28 이메일로 사용자 검색 메서드
    public Member getMemberByEmail(String email) {
        Optional<Member> member = this.memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new NotFoundException("Member not found");
        }
    }

    // 24.07.04 리액트에서 넘어온 정보로 로그인 확인
    public Member getMemberByIdAndPassword(String id, String password) {
        Optional<Member> _member = this.memberRepository.findByUsername(id);

        Member realMember;
        if (_member.isPresent()) {
            realMember = _member.get();  // 실제 이름의 사용자정보가 넘어온다 => 암호화된 비밀번호까지
            // plain text와 암호화된 값이 같은 값을 가지고 있는지 확인
            boolean match = passwordEncoder.matches(password, realMember.getPassword());

            if (match)
                return realMember;
            else
                throw new NotFoundException("이걸 까먹네ㅋㅋ");

        } else {
            throw new NotFoundException("이걸 까먹네ㅋㅋ");
        }
    }
}