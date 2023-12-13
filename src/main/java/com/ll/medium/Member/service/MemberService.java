package com.ll.medium.Member.service;


import com.ll.medium.Member.entity.Member;
import com.ll.medium.Member.entity.MemberForm;
import com.ll.medium.Member.repository.MemberRepository;
import com.ll.medium.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void joinMember(MemberForm memberForm) {
        Member member = memberRepository.save(
                Member.builder()
                        .username(memberForm.getUsername())
                        .password(memberForm.getPassword())
                        .build());
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
    }
}
