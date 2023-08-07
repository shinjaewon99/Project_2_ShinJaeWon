package com.likelion.sns.article.likeArticle.service;

import com.likelion.sns.article.feed.domain.Feed;
import com.likelion.sns.article.feed.domain.FeedRepository;
import com.likelion.sns.article.likeArticle.domain.LikeArticle;
import com.likelion.sns.article.likeArticle.domain.LikeArticleRepository;
import com.likelion.sns.article.likeArticle.dto.response.LikeArticleCommonResponse;
import com.likelion.sns.article.likeArticle.exception.NotMyFeedLikeException;
import com.likelion.sns.global.exception.NotExistUserException;
import com.likelion.sns.user.domain.User;
import com.likelion.sns.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeArticleService {
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final LikeArticleRepository likeArticleRepository;
    private static long compare = 0; // userId 저장 변수
    private static Integer likeCount = 0; // 좋아요 갯수 저장 변수

    public LikeArticleCommonResponse likeFeed(final Long userId, final Long feedId) {
        User findUser = validateExistUser(userId);
        Feed findFeed = validateExistFeed(feedId);

        validateOwnerFeed(findUser, findFeed); // 본인의 피드인지 검증

        LikeArticle likeArticle = likePushFeed(findUser, findFeed); // 연관관계 메소드 실행

        LikeArticleCommonResponse likeArticleCommonResponse = new LikeArticleCommonResponse();

        // 이미 좋아요를 한 userId가 있는지 검증
        if (likeArticle.getUserId().getId() == compare) {
            likeArticleRepository.delete(likeArticle);
            likeArticleCommonResponse.setMessage("기존의 좋아요가 삭제되었습니다.");
            likeArticle.setLikeCount(--likeCount); // 전위 연산자를 통한 좋아요 갯수 감소
            compare = 0; // userId 초기화
        } else {
            likeArticleRepository.save(likeArticle);
            likeArticleCommonResponse.setMessage("좋아요가 등록되었습니다.");
            likeArticle.setLikeCount(++likeCount); // 전위 연산자를 통한 좋아요 갯수 증가
            compare = likeArticle.getUserId().getId(); // compare에 userId 값 할당
        }

        likeArticleRepository.flush(); // 변경된 데이터를 데이터베이스에 반영

        return likeArticleCommonResponse;
    }

    private LikeArticle likePushFeed(User findUser, Feed findFeed) {
        return new LikeArticle(findUser, findFeed); // 생성자를 통해 연관관계 메소드 설정
    }

    private User validateExistUser(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotExistUserException("등록된 회원이 없습니다."));
    }

    private Feed validateExistFeed(final Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(() -> new NotExistUserException("등록된 피드가 없습니다."));
    }

    private void validateOwnerFeed(final User findUser, final Feed findFeed) {
        if (findUser.getId().equals(findFeed.getUserId().getId())) {
            throw new NotMyFeedLikeException("본인의 피드에는 좋아요 할수 없습니다.");
        }
    }
}
