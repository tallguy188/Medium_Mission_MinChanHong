package com.ll.medium.post.controller;


import com.ll.medium.member.entity.Member;
import com.ll.medium.member.service.MemberService;
import com.ll.medium.post.entity.Post;
import com.ll.medium.post.entity.PostForm;
import com.ll.medium.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    // 글 작성
    @GetMapping("/write")
    public String createPost(PostForm postForm) {
        return "post_form";
    }

    @PostMapping("/write")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "article_form";
        }
        // findMemberByUsername에서 이미 null을 잡아냄
        Member optionalMember = memberService.findMemberByUsername(principal.getName());
        postForm.setWriter(optionalMember);
        postService.create(postForm);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Integer id,Principal principal, BindingResult bindingResult, PostForm postForm ){

        if(bindingResult.hasErrors()) {
            return "redirect:/post/"+id;
        }
        Post post = postService.getPostById(id);

        if(!post.getMember().getUsername().equals(principal.getName())) {
            // post 안되게
        }

        postService.deletePost(post);

        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0") int page) {

        Page<Post> paging = postService.findList(page);
        model.addAttribute("postList",paging);
        return "postList_form";
    }

    @GetMapping("/myList")
    @PreAuthorize("isAuthenticated()")
    public String myList (Model model,Principal principal, @RequestParam(value="page", defaultValue="0") int page) {

        Member writer = memberService.findMemberByUsername(principal.getName());
        Page<Post> post = postService.getPostByUsername(writer,page);
        model.addAttribute("postList",post);
        return "myList_form";
    }



}
