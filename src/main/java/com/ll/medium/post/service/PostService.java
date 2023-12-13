package com.ll.medium.post.service;


import com.ll.medium.member.repository.MemberRepository;
import com.ll.medium.post.entity.Post;
import com.ll.medium.post.entity.PostForm;
import com.ll.medium.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    // post생성
    public void create(PostForm postForm) {
        Post post = postRepository.save(Post.builder()
                        .title(postForm.getTitle())
                        .content(postForm.getContent())
                        .member(postForm.getWriter())
                        .isPublished(postForm.isPublished())
                        .dateTime(LocalDateTime.now())
                .build());
    }



}
