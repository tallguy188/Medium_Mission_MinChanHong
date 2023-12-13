package com.ll.medium.post.controller;


import com.ll.medium.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
}
