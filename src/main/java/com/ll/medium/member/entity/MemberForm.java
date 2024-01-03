package com.ll.medium.member.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    @Size(min = 3, max = 25)
    @NotBlank(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordConfirm;

    public static MemberForm generate(String username, String password, String passwordConfirm) {
        MemberForm memberForm = new MemberForm();
        memberForm.setUsername(username);
        memberForm.setPassword(password);
        memberForm.setPasswordConfirm(passwordConfirm);
        return memberForm;
    }

}