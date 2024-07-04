package com.come1997.backboard.restcontroller;

import com.come1997.backboard.dto.Header;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member") // 기본 경로 설정
@Log4j2
public class RestMemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public Header<Member> login(@RequestParam Map<String, String> loginInfo) {
        log.info(String.format(">>>>>>>>>>>>>>>> 리액트 정보 : %s", loginInfo.get("id")));

        String id = loginInfo.get("id");
        String password = loginInfo.get("password");

        try {
            Member member = this.memberService.getMemberByIdAndPassword(id, password);

            if (member != null) {
                Header<Member> result = Header.OK(member);
                return result;
            } else {
                Header<Member> fail = Header.OK("이걸 까먹네ㅋㅋ");
                return fail;
            }
        } catch (Exception e) {
            log.catching(e);

            Header<Member> fail = Header.OK("이걸 까먹네ㅋㅋ");
            return fail;
        }
    }
}
