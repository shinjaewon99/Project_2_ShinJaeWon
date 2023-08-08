package com.likelion.sns.article.feed.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedReedOneResponse {

    private String title;
    private String content;
    private String imgUrl;
    private List<String> commentList;
    private Integer likeCount;

    public FeedReedOneResponse(final String title, final String content, final String imgUrl,
                               final List<String> commentList, final Integer likeCount) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.commentList = commentList;
        this.likeCount = likeCount;
    }
}
