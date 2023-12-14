package com.ll.medium.post.repository;

import com.ll.medium.member.entity.Member;
import com.ll.medium.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> getPostById(Integer id);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByPublished(Pageable pageable, Boolean bool);

    Page<Post> findAllByWriter(Member writer, Pageable pageable);
}
