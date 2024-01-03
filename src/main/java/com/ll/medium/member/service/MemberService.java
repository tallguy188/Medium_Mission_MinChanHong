package com.ll.medium.member.service;


import com.ll.medium.global.exception.MemberNotFoundException;
import com.ll.medium.member.entity.Member;
import com.ll.medium.member.entity.MemberForm;
import com.ll.medium.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void joinMember(MemberForm memberForm) {

        findMemberByUsername(memberForm.getUsername());
        Member member = memberRepository.save(
                Member.builder()
                        .username(memberForm.getUsername())
                        .password(passwordEncoder.encode(memberForm.getPassword()))
                        .build());
    }

    public Member findMemberByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        if (member.isPresent()) {
            return member.get();
        }
        else {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    public long count() {
        return memberRepository.count();
    }
}
