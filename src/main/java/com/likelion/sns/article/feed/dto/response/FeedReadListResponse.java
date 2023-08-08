package com.likelion.sns.article.feed.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedReadListResponse {

    private String title;
    private String content;
    private String topImgUrl;

    public FeedReadListResponse(final String title, final String content, final String topImgUrl) {
        this.title = title;
        this.content = content;
        this.topImgUrl = topImgUrl;
    }
}
