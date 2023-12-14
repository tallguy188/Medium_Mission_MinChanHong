package com.ll.medium.post.service;


import com.ll.medium.global.exception.PostNotFoundException;
import com.ll.medium.member.entity.Member;
import com.ll.medium.member.repository.MemberRepository;
import com.ll.medium.post.entity.Post;
import com.ll.medium.post.entity.PostForm;
import com.ll.medium.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    // 리스트 가져오기(isPublished)
    public Page<Post> findList(int page) {
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(list));
        return postRepository.findAllByPublished(pageable,true);
    }

    // username으로 페이지 가져오기
    public Page<Post> getPostByUsername(Member writer, int page) {
        List<Sort.Order> myList = new ArrayList<>();
        myList.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(myList));
        return postRepository.findAllByWriter(writer,pageable);


    }
}
