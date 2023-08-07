package com.likelion.sns.article.comment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWriteRequest {
    private String writer;
    private String content;
}
