## 8일차

### 목차

1. Spring Boot JPA 프로젝트 개발 계속
    - Spring Security
    - 로그인 기능 추가

### **1. Spring Boot JPA 프로젝트 개발**
1. `/security/SecurityConfig.java`에 `BCryptPasswordEncoder`를 빈으로 작업한다.
```java
package com.come1997.backboard.service;


import com.come1997.backboard.entity.Member;
import com.come1997.backboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member setMember(String username, String email, String password) {
        // password는 암호화 때문에 포함하지 않는다
        Member member = Member.builder().username(username).email(email).build();
        // Bean을 등록해서 쓰는게 유지보수를 위해 더 좋다
        member.setPassword(passwordEncoder.encode(password));   // 암호화한 값을 DB에 저장
        this.memberRepository.save(member);

        return member;
    }
}

```

2. `MemebrController` 수정, `Member` 작성



3. `registar.html` 작성
- Db에 저장된 정보 확인

4. `layout.html` 회원가입 링크 추가

5. `MemberController`에 중복회원가입 방지 추가


>> 시큐리티 끝!!!!

6. 로그인 기능 추가
    - `/security/SecurityConfig`에 로그인 url 설정
    - `layout.html` 로그인 링크 수정
    - `login.html`작성
    - `/repository/MemberRepository`에 `find*` 메서드 추가
    - `controller/MemberController`에 login `Get`, `Post`메서드 작성