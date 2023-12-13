package com.ll.medium.global.exception;

public class PostNotFoundException extends RuntimeException{

    private final Integer postId;
    public PostNotFoundException(Integer postId) {
        super("Post not found with id:" + postId);
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }
}
