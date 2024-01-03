package com.ll.medium.post.entity;


import com.ll.medium.member.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostForm {

    @NotEmpty(message = "제목을 입력해주세요")
    private String title;
    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    private Member writer;

    private LocalDateTime dateTime = LocalDateTime.now();

    private boolean isPublished = true;

    public static PostForm generate(String title, String content, Member writer, boolean isPublished) {
        PostForm postForm  = new PostForm();
        postForm.setTitle(title);
        postForm.setContent(content);
        postForm.setWriter(writer);
        postForm.setPublished(isPublished);
        return postForm;
    }
}
