package com.likelion.sns.article.comment.service;

import com.likelion.sns.article.comment.domain.Comment;
import com.likelion.sns.article.comment.domain.CommentRepository;
import com.likelion.sns.article.comment.dto.request.CommentModifyRequest;
import com.likelion.sns.article.comment.dto.request.CommentWriteRequest;
import com.likelion.sns.article.comment.dto.response.CommentCommonResponse;
import com.likelion.sns.article.feed.domain.Feed;
import com.likelion.sns.article.feed.domain.FeedRepository;
import com.likelion.sns.global.exception.NotExistUserException;
import com.likelion.sns.global.exception.NotInputException;
import com.likelion.sns.user.domain.User;
import com.likelion.sns.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성 메소드
    public CommentCommonResponse writeComment(final Long userId, final Long feedId,
                                              final CommentWriteRequest writeRequest) {
        User findUser = validateExistUser(userId);
        Feed findFeed = validateExistFeed(feedId);
        validateWriter(writeRequest.getWriter());
        validateContent(writeRequest.getContent());


        Comment writeComment = new Comment(writeRequest.getWriter(), writeRequest.getContent());
        writeComment.setUser(findUser); // 연관관계 메소드 호출
        writeComment.setFeed(findFeed); // 연관관계 메소드 호출

        commentRepository.save(writeComment);

        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        commentCommonResponse.setMessage("댓글이 등록 되었습니다.");

        return commentCommonResponse;
    }

    // 댓글 수정 메소드
    public CommentCommonResponse modifyComment(final Long userId, final Long feedId, final Long commentId,
                                               final CommentModifyRequest modifyRequest) {
        User findUser = validateExistUser(userId);
        Feed findFeed = validateExistFeed(feedId);
        Comment findComment = validateExistComment(commentId);
        validateContent(modifyRequest.getContent());

        findComment.setUser(findUser); // 연관관계 메소드 호출
        findComment.setFeed(findFeed); // 연관관계 메소드 호출

        findComment.modifyComment(modifyRequest.getContent());

        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        commentCommonResponse.setMessage("댓글의 내용이 수정 되었습니다.");

        return commentCommonResponse;
    }

    // 댓글 논리 삭제 메소드
    public CommentCommonResponse softDeleteComment(final Long userId,
                                                   final Long feedId,
                                                   final Long commentId) {
        validateExistUser(userId);
        validateExistFeed(feedId);
        validateExistComment(commentId);

        commentRepository.deleteById(feedId);

        CommentCommonResponse response = new CommentCommonResponse();
        response.setMessage("댓글 삭제가 완료되었습니다.");

        return response;
    }


    private User validateExistUser(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotExistUserException("등록된 회원이 없습니다."));
    }

    private Feed validateExistFeed(final Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(() -> new NotExistUserException("등록된 피드가 없습니다."));
    }

    private Comment validateExistComment(final Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotExistUserException("등록된 댓글이 없습니다."));
    }

    private void validateWriter(String writer) {
        if (writer == null) {
            throw new NotInputException("작성자를 입력해주세요.");
        }
    }

    private void validateContent(String content) {
        if (content == null) {
            throw new NotInputException("댓글 내용을 입력해주세요.");
        }
    }
}
