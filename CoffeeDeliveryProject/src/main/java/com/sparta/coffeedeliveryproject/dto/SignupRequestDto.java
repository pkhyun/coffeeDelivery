package com.sparta.coffeedeliveryproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "ID는 최소 4글자 이상, 10글자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "사용자 ID는 알파벳 대소문자, 숫자로만 구성되어야 합니다.")
    private String userName;

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8글자 이상, 15글자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])\\S{8,}$", message = "password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로만 구성되어야 합니다.")
    private String password;

    @NotBlank
    private String nickName;

    private String role = "";

    private String adminToken = "";

}
