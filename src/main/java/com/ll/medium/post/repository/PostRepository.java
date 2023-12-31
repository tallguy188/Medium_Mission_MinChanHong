package com.ll.medium.post.repository;

import com.ll.medium.member.entity.Member;
import com.ll.medium.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> getPostById(Long id);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByIsPublished(Pageable pageable, Boolean bool);

    Page<Post> findAllByMember(Member writer, Pageable pageable);

    List<Post> findAllPostByMember(Member member);
}
