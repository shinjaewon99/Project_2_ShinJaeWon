package com.likelion.sns.article.feed.controller;

import com.likelion.sns.article.feed.dto.request.CreateFeedRequest;
import com.likelion.sns.article.feed.dto.response.FeedCommonResponse;
import com.likelion.sns.article.feed.service.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/{userId}/feed")
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/create")
    public ResponseEntity<FeedCommonResponse> createFeed(@PathVariable Long userId,
                                                         final Authentication authentication,
                                                         @Valid @RequestBody final CreateFeedRequest createRequest) {
        return ResponseEntity.ok(feedService.createFeed(userId, authentication, createRequest));
    }

//    @PostMapping("/{feedId}/postImg")
//    public ResponseEntity<FeedCommonResponse> postImg(@PathVariable Long feedId,
//                                                      final Authentication authentication,
//                                                      final MultipartFile img) {
//        return ResponseEntity.ok(feedService.feedPostImg(feedId, authentication, img));
//    }
//
//    @GetMapping("/{feedId}/readOne")
//    public ResponseEntity<ReadFeedResponse> readOneFeed(@PathVariable final Long feedId,
//                                                        final Authentication authentication) {
//        return ResponseEntity.ok(feedService.reedSingleFeed(feedId, authentication));
//    }

    @DeleteMapping("/{feedId}/delete")
    public ResponseEntity<FeedCommonResponse> softDeleteFeed(@PathVariable Long userId,
                                                             @PathVariable final Long feedId,
                                                             final Authentication authentication) {
        return ResponseEntity.ok(feedService.softDeleteFeed(userId, feedId, authentication));
    }
}
