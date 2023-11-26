package com.likelion.sns.article.feed.controller;

import com.likelion.sns.article.feed.dto.request.CreateFeedRequest;
import com.likelion.sns.article.feed.dto.response.FeedCommonResponse;
import com.likelion.sns.article.feed.dto.response.FeedReadListResponse;
import com.likelion.sns.article.feed.dto.response.FeedReedOneResponse;
import com.likelion.sns.article.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/{userId}/feed")
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/create")
    public ResponseEntity<FeedCommonResponse> createFeed(@PathVariable final Long userId,
                                                         @RequestBody final CreateFeedRequest createRequest) {
        return ResponseEntity.ok(feedService.createFeed(userId, createRequest));
    }

    @PostMapping("/{feedId}/postImg")
    public ResponseEntity<FeedCommonResponse> postImg(@PathVariable final Long userId,
                                                      @PathVariable final Long feedId,
                                                      @RequestParam("image") final MultipartFile img) throws IOException {
        return ResponseEntity.ok(feedService.feedPostImg(userId, feedId, img));
    }

    @GetMapping("/{feedId}/readLists")
    public ResponseEntity<FeedReadListResponse> readListFeed(@PathVariable final Long userId,
                                                             @PathVariable final Long feedId){

        return ResponseEntity.ok(feedService.readListFeed(userId, feedId));
    }


    @GetMapping("/{feedId}/readOne")
    public ResponseEntity<FeedReedOneResponse> readOneFeed(@PathVariable final Long userId,
                                                           @PathVariable final Long feedId) {
        return ResponseEntity.ok(feedService.reedSingleFeed(userId, feedId));
    }

    @DeleteMapping("/{feedId}/delete")
    public ResponseEntity<FeedCommonResponse> softDeleteFeed(@PathVariable final Long userId,
                                                             @PathVariable final Long feedId) {
        return ResponseEntity.ok(feedService.softDeleteFeed(userId, feedId));
    }
}
