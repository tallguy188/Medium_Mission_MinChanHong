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


    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0") int page) {

        Page<Post> paging = postService.findList(page);
        model.addAttribute("postList",paging);
        return "post_list";
    }

    @GetMapping("/write")
    public String createPost(PostForm postForm) {
        return "post_form";
    }

    @PostMapping("/write")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "post_form";
        }

        try{
            Member member = memberService.findMemberByUsername(principal.getName());
            postForm.setWriter(member);
        } catch (NullPointerException e){
            postForm.setWriter(null);
        }
        postService.create(postForm);

        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long id,Principal principal, BindingResult bindingResult, PostForm postForm ){

        if(bindingResult.hasErrors()) {
            return "redirect:/post/"+id;
        }
        Post post = postService.getPostById(id);

        if(!post.getMember().getUsername().equals(principal.getName())) {
            throw new RuntimeException("권한없음");
        }

        postService.deletePost(post);

        return "redirect:/post/list";
    }

    @GetMapping("/myList")
    @PreAuthorize("isAuthenticated()")
    public String myList (Model model,Principal principal, @RequestParam(value="page", defaultValue="0") int page) {

        Member writer = memberService.findMemberByUsername(principal.getName());
        Page<Post> post = postService.getPostByUsername(writer,page);
        model.addAttribute("postList",post);
        return "myList_form";
    }


    @GetMapping("/{postId}")
    public String postDetail(@PathVariable("postId") Long id, Principal principal, Model model) {
        Post detailPost = postService.getPostDetail(id);
        model.addAttribute("post",detailPost);
        return "post_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}/modify")
    public String modify(@PathVariable("postId") Long id,Principal principal, PostForm postForm, Model model){
        Post modiPost = postService.getPostById(id);
        // 로그인사용자와 게시물 작성자가 다를경우

        if(!modiPost.getMember().getUsername().equals(principal.getName())) {
            throw new RuntimeException("수정권한이 없습니다.");
        }

        postForm.setTitle(modiPost.getTitle());
        postForm.setContent(modiPost.getContent());

        model.addAttribute("postForm",postForm);
        model.addAttribute("postId",id);

        return "modify_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/modify")
    public String modifyPost(@PathVariable("postId") Long id, BindingResult bindingResult, Principal principal,PostForm postForm) {
        if(bindingResult.hasErrors()) {
            return "modify_form";
        }
        Post modiPost = postService.getPostById(id);

        postService.modify(modiPost, postForm);

        return "redirect:/post/"+id;
    }





}
