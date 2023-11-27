package com.likelion.sns.article.likeArticle.controller;

import com.likelion.sns.article.likeArticle.dto.response.LikeArticleCommonResponse;
import com.likelion.sns.article.likeArticle.service.LikeArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/{userId}/feed/{feedId}/like")
public class LikeArticleController {
    private final LikeArticleService likeArticleService;

    @PostMapping
    public ResponseEntity<LikeArticleCommonResponse> likePush(@PathVariable final Long userId,
                                                              @PathVariable final Long feedId) {

        return ResponseEntity.ok().body(likeArticleService.likeFeed(userId, feedId));
    }
}
