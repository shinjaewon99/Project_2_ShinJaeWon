package com.likelion.sns.article.comment.service;

import com.likelion.sns.article.comment.domain.Comment;
import com.likelion.sns.article.comment.domain.CommentRepository;
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

@Service
@RequiredArgsConstructor
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

    private User validateExistUser(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotExistUserException("등록된 회원이 없습니다."));
    }

    private Feed validateExistFeed(final Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(() -> new NotExistUserException("등록된 피드가 없습니다."));
    }

    private void validateWriter(String writer) {
        if(writer == null){
            throw new NotInputException("작성자를 입력해주세요.");
        }
    }

    private void validateContent(String content) {
        if(content == null){
            throw new NotInputException("댓글 내용을 입력해주세요.");
        }
    }
}
