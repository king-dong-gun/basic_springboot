package com.come1997.backboard.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ReplyForm {

    @NotBlank(message = "내용은 필수입니다!")
    private String content;

}
