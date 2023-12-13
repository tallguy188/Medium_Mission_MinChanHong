package com.ll.medium.member.controller;


import com.ll.medium.member.entity.MemberForm;
import com.ll.medium.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/join")
    public String join(MemberForm memberForm) {
        return "join_form";
    }

    @PostMapping("/join")
    public String join(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "join_form";
        }
        if(!memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
            bindingResult.rejectValue("password","passwordConfirm", "비밀번호가 일치하지 않습니다.");
        }
        try{
            memberService.joinMember(memberForm);
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("등록된 사용자입니다.");
            return "join_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("Failed",e.getMessage());
        }
        return "redirect:/member/login";

    }



}
