package com.come1997.backboard.service;

import com.come1997.backboard.entity.Member;
import com.come1997.backboard.repository.MemberRepository;
import com.come1997.backboard.security.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> _member = this.memberRepository.findByUsername(username);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("입력하신 회원을 찾을수 없습니다!");
        }
        Member member = _member.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if("admin".equals(username)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
    }
}
