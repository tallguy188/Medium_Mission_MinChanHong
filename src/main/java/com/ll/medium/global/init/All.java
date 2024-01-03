package com.ll.medium.global.init;

import com.ll.medium.member.entity.MemberForm;
import com.ll.medium.member.service.MemberService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class All {
    private final MemberService memberService;

    @Bean
    @Order(2)
    public ApplicationRunner initAll() {
        return args -> {
            if (memberService.count() > 0) return;


            MemberForm memberForm1 = new MemberForm();
            memberForm1.setUsername("system");
            memberForm1.setPassword("1234");
            memberForm1.setPasswordConfirm("1234");

            MemberForm memberForm2 = new MemberForm();
            memberForm1.setUsername("admin");
            memberForm1.setPassword("1234");
            memberForm1.setPasswordConfirm("1234");


            memberService.joinMember(memberForm1);
            memberService.joinMember(memberForm2);
        };
    }
}