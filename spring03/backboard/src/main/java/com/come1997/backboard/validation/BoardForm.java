package com.come1997.backboard.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class BoardForm {

    @Size(max = 250)
    // @NotEmpty(message = "제목은 필수입니다!")   // 스페이스를 허용한다.
    @NotBlank(message = "제목은 필수입니다!")
    private String title;

//    @NotEmpty(message = "내용은 필수입니다!")
    @NotBlank(message = "내용은 필수입니다!")
    private String content;
}
