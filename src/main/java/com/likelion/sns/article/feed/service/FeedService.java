package com.likelion.sns.article.feed.service;

import com.likelion.sns.article.comment.domain.CommentRepository;
import com.likelion.sns.article.feed.domain.Feed;
import com.likelion.sns.article.feed.domain.FeedRepository;
import com.likelion.sns.article.feed.dto.request.CreateFeedRequest;
import com.likelion.sns.article.feed.dto.response.FeedCommonResponse;
import com.likelion.sns.user.domain.User;
import com.likelion.sns.user.domain.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FeedService {
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;


    public FeedCommonResponse createFeed(final Long userId,
                                         final Authentication authentication,
                                         final CreateFeedRequest createRequest) {
        validateExistUser(userId);
        String username = authentication.getName();
        User findUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());

        Feed feed = Feed.dtoToEntity(createRequest);

        feed.setUser(findUser); // 연관관계 메소드 호출
        findUser.addFeed(feed); // 연관관계 메서드 호출

        feedRepository.save(feed);

        FeedCommonResponse response = new FeedCommonResponse();
        response.setMessage("피드 등록이 완료되었습니다.");

        return response;
    }

//    public FeedCommonResponse feedPostImg(final Long feedId,
//                                          final Authentication authentication,
//                                          final MultipartFile image) {
//        String username = authentication.getName();
//        User findUserId = userRepository
//                .findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("로그인한 회원이 아닙니다."));
//        Feed findFeedId = feedRepository
//                .findById(feedId).get();
//        /*orElseThrow(() -> new UsernameNotFoundException("로그인한 회원이 아닙니다."));*/
//        log.info("feedId : {}", findFeedId.getId());
//        String imageLocation = String.format("feedImage/%d/", findUserId.getId());
//        String imageName = image.getOriginalFilename();
//        String imagePath = imageLocation + imageName;
//
//        try {
//            Files.createDirectories(Path.of(imageLocation)); // 파일 업로드 위치
//        } catch (IOException exception) {
//            throw new IllegalArgumentException();
//        }
//
//        try {
//            image.transferTo(Path.of(imagePath)); // 파일 업로드 처리
//        } catch (IOException exception) {
//            throw new IllegalArgumentException();
//        }
//
//
//        List<ArticleImage> articleImages = findFeedId.getArticleImages();
//        for (ArticleImage articleImage : articleImages) {
//            articleImage.uploadImg(imagePath);
//        }
//
//        feedRepository.save(findFeedId);
//
//        FeedCommonResponse response = new FeedCommonResponse();
//        response.setMessage("이미지 등록이 완료되었습니다.");
//
//        return response;
//    }
//
//    public ReadFeedResponse reedSingleFeed(final Long feedId, final Authentication authentication) {
//        feedRepository.findById(feedId).orElseThrow(() -> new EntityNotFoundException());
//        String username = authentication.getName();
//        String findProfileImg = userRepository.findByProfileImg(username);
//        String findComment = commentRepository.findByContent(username);
//
//
//        return new ReadFeedResponse(findProfileImg, findComment);
//    }

    public FeedCommonResponse softDeleteFeed(final Long userId,
                                             final Long feedId,
                                             final Authentication authentication) {
        validateExistUser(userId);
        validateExistFeed(feedId);
        validateAuthUser(authentication);

        feedRepository.deleteById(feedId);

        FeedCommonResponse response = new FeedCommonResponse();
        response.setMessage("피드 삭제가 완료되었습니다.");

        return response;
    }

    private void validateAuthUser(Authentication authentication) {
        String username = authentication.getName();
        userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());
    }

    private void validateExistUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
    }
    private void validateExistFeed(Long feedId) {
        feedRepository.findById(feedId).orElseThrow(() -> new EntityNotFoundException());
    }
}
