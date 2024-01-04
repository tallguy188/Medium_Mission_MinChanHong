package com.ll.medium.global.init;

import com.ll.medium.member.entity.Member;
import com.ll.medium.member.entity.MemberForm;
import com.ll.medium.member.service.MemberService;
import com.ll.medium.post.entity.PostForm;
import com.ll.medium.post.service.PostService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Configuration
@Profile("!prod")
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final PostService postService;

    @Bean
    @Order(3)
    public ApplicationRunner initNotProd() {
        return args -> {

            MemberForm memberForm1 = MemberForm.generate("user1","1234","1234");
            MemberForm memberForm2 = MemberForm.generate("user2","1234","1234");
            MemberForm memberForm3 = MemberForm.generate("user3","1234","1234");

            memberService.joinMember(memberForm1);
            memberService.joinMember(memberForm2);
            memberService.joinMember(memberForm3);

            Member author1 = memberService.findMemberByUsername("user1");
            Member author2 = memberService.findMemberByUsername("user2");
            Member author3 = memberService.findMemberByUsername("user3");


            PostForm postForm1 = PostForm.generate("title1","content1",author1,true);
            PostForm postForm2 = PostForm.generate("title2","content2",author2,false);
            PostForm postForm3 = PostForm.generate("title3","content3",author3,false);

            postService.create(postForm1);
            postService.create(postForm2);
            postService.create(postForm3);

            for (int i = 4; i < 31; i++) {
                MemberForm memberCreateForm = new MemberForm();
                memberCreateForm.setUsername("user" + i);
                memberCreateForm.setPassword("1234");
                memberCreateForm.setPasswordConfirm("1234");
                memberService.joinMember(memberCreateForm);

                PostForm postForm = new PostForm();
                postForm.setTitle("title" + i);
                postForm.setContent("content" + i);
                postForm.setWriter(memberService.findMemberByUsername("user" + i));
                postService.create(postForm);
            }
            createPaidMembers();
            createPaidPosts();
        };
    }

    private void createPaidMembers() {
        for (int i=0; i<=100; i++) {
            MemberForm memberPaidForm = new MemberForm();
            memberPaidForm.setUsername("paid_user"+i);
            memberPaidForm.setPassword("1234");
            memberPaidForm.setPasswordConfirm("1234");
            memberPaidForm.setPaid(true);
            memberService.joinMember(memberPaidForm);
        }
    }

    private void createPaidPosts() {
        for(int i=0; i<=100; i++) {
            PostForm postPaidForm= new PostForm();
            postPaidForm.setTitle("Paid post"+i);
            postPaidForm.setContent("Content for Paid Post"+i);
            postPaidForm.setWriter(memberService.findMemberByUsername("paid_user" +i));
            postPaidForm.setPaid(true);
            postService.create(postPaidForm);
        }
    }
}