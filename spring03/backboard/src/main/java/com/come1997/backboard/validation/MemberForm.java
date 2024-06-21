package com.come1997.backboard.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @Size(min = 4, max = 40)
    @NotBlank(message = "이름을 입력해주세요!")
    private String username;

    @Email
    @NotBlank(message = "이메일을 입력해주세요!")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요!")
    private String password1;   // 비밀번호 확인을 위해 password1, password2로 선언

    @NotBlank(message = "비밀번호 확인을 입력해주세요!")
    private String password2;

}
