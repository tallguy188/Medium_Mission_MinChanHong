package com.ll.medium.post.service;


import com.ll.medium.global.exception.PostNotFoundException;
import com.ll.medium.member.repository.MemberRepository;
import com.ll.medium.post.entity.Post;
import com.ll.medium.post.entity.PostForm;
import com.ll.medium.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    // post 생성
    public void create(PostForm postForm) {
        Post post = postRepository.save(Post.builder()
                        .title(postForm.getTitle())
                        .content(postForm.getContent())
                        .member(postForm.getWriter())
                        .isPublished(postForm.isPublished())
                        .dateTime(LocalDateTime.now())
                .build());
    }

    public void deletePost(Post post) {

        // 이미 getPostById에서 예외처리를 해서 여기서 null이 나올리가 없음
            postRepository.delete(post);
    }

    // post id로 찾기
    public Post getPostById(Integer id) {
          Optional<Post> post = postRepository.getPostById(id);
          if(post.isPresent()) {
              return post.get();
          }
          else {
              throw new PostNotFoundException(id);
          }
    }

}
