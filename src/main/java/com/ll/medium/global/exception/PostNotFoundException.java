package com.ll.medium.global.exception;

public class PostNotFoundException extends RuntimeException{

    private final Long postId;
    public PostNotFoundException(Long postId) {
        super("Post not found with id:" + postId);
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
}
