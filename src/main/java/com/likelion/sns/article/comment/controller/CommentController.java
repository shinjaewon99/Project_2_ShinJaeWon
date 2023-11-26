package com.likelion.sns.article.comment.controller;

import com.likelion.sns.article.comment.dto.request.CommentModifyRequest;
import com.likelion.sns.article.comment.dto.request.CommentWriteRequest;
import com.likelion.sns.article.comment.dto.response.CommentCommonResponse;
import com.likelion.sns.article.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/{userId}/feed/{feedId}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity<CommentCommonResponse> writeComment(@PathVariable final Long userId,
                                                              @PathVariable final Long feedId,
                                                              @RequestBody final CommentWriteRequest writeRequest) {

        return ResponseEntity.ok().body(commentService.writeComment(userId, feedId, writeRequest));
    }

    @PutMapping("{commentId}/modify")
    public ResponseEntity<CommentCommonResponse> modifyComment(@PathVariable final Long userId,
                                                               @PathVariable final Long feedId,
                                                               @PathVariable final Long commentId,
                                                               @RequestBody final CommentModifyRequest modifyRequest) {

        return ResponseEntity.ok().body(commentService.modifyComment(userId, feedId, commentId, modifyRequest));
    }

    @DeleteMapping("{commentId}/delete")
    public ResponseEntity<CommentCommonResponse> softDeleteComment(@PathVariable final Long userId,
                                                                   @PathVariable final Long feedId,
                                                                   @PathVariable final Long commentId) {

        return ResponseEntity.ok().body(commentService.softDeleteComment(userId, feedId, commentId));
    }
}
