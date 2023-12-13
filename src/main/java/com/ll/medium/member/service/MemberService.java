package com.ll.medium.member.service;


import com.ll.medium.member.entity.Member;
import com.ll.medium.member.entity.MemberForm;
import com.ll.medium.member.repository.MemberRepository;
import com.ll.medium.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public void joinMember(MemberForm memberForm) {
        Member member = memberRepository.save(
                Member.builder()
                        .username(memberForm.getUsername())
                        .password(passwordEncoder.encode(memberForm.getPassword()))
                        .build());
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
    }
}
