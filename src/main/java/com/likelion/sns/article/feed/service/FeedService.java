package com.likelion.sns.article.feed.service;

import com.likelion.sns.article.articleImage.domain.ArticleImage;
import com.likelion.sns.article.articleImage.domain.ArticleImageRepository;
import com.likelion.sns.article.comment.domain.CommentRepository;
import com.likelion.sns.article.feed.domain.Feed;
import com.likelion.sns.article.feed.domain.FeedRepository;
import com.likelion.sns.article.feed.dto.request.CreateFeedRequest;
import com.likelion.sns.article.feed.dto.response.FeedCommonResponse;
import com.likelion.sns.article.feed.dto.response.FeedReadOneResponse;
import com.likelion.sns.global.exception.NotExistUserException;
import com.likelion.sns.global.exception.NotInputException;
import com.likelion.sns.user.domain.User;
import com.likelion.sns.user.domain.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FeedService {
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final ArticleImageRepository articleImageRepository;

    // 피드 등록 메소드
    public FeedCommonResponse createFeed(final Long userId,
                                         final CreateFeedRequest createRequest) {
        User findUser = validateExistUser(userId);// 등록된 회원인지 검증
        validateTitle(createRequest.getTitle()); // 제목 미입력 검증
        validateContent(createRequest.getContent()); // 내용 미입력 검증

        Feed feed = new Feed(createRequest.getTitle(), createRequest.getContent());

        feed.setUser(findUser); // 연관관계 메소드 호출
        findUser.addFeed(feed); // 연관관계 메서드 호출

        feedRepository.save(feed);

        FeedCommonResponse response = new FeedCommonResponse();
        response.setMessage("피드 등록이 완료되었습니다.");

        return response;
    }


    // 피드 이미지 등록 메소드
    public FeedCommonResponse feedPostImg(final Long userId,
                                          final Long feedId,
                                          final MultipartFile image) throws IOException {

        validateExistUser(userId); // 등록된 회원인지 검증
        Feed feed = validateExistFeed(feedId);// 등록된 피드인지 검증


        String imageLocation = String.format("feed_images/%d/", feed.getId());
        String imageName = image.getOriginalFilename();
        String imagePath = imageLocation + imageName;

        try {
            Files.createDirectories(Path.of(imageLocation)); // 파일 업로드 위치
        } catch (IOException exception) {
            throw new IllegalArgumentException();
        }
        try {
            image.transferTo(Path.of(imagePath)); // 파일 업로드 처리
        } catch (IOException exception) {
            throw new IllegalArgumentException();
        }


        ArticleImage articleImage = Feed.createArticleImage(imagePath);

        articleImage.setFeed(feed); // 연관관계 메소드 호출
        feed.addArticleImage(articleImage); // 연관관계 메소드 호출

        feedRepository.save(feed);
        articleImageRepository.save(articleImage);

        FeedCommonResponse response = new FeedCommonResponse();
        response.setMessage("이미지 등록이 완료되었습니다.");

        return response;
    }


    // 페이징 조회
//    @Transactional(readOnly = true)
//    public Page<FeedReadPageResponse> readPageFeed(final Long userId, final Long feedId,
//                                                   final Integer page, final Integer limit) {
//
//        validateExistUser(userId);
//        Feed findFeed = validateExistFeed(feedId);
//        if(findFeed.getArticleImages().isEmpty()){
//
//        };
//        PageRequest pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
//        Page<Feed> feedPage = feedRepository.findAll(pageable);
//
//        return
//    }

    // 단일 조회
    @Transactional(readOnly = true)
    public FeedReadOneResponse reedSingleFeed(final Long userId, final Long feedId) {
        validateExistUser(userId); // 등록된 회원인지 검증
        Feed findFeed = validateExistFeed(feedId); // 등록된 피드인지 검증

        return new FeedReadOneResponse(findFeed.getTitle(), findFeed.getContent(), findFeed.getArticleImages(),
                findFeed.getComments(), findFeed.getFeedIdLikeArticles());
    }

//    public FeedCommonResponse updateFeed() {
//
//    }

    // 피드 논리 삭제 메소드
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

    private User validateExistUser(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotExistUserException("등록된 회원이 없습니다."));
    }

    private Feed validateExistFeed(final Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(() -> new NotExistUserException("등록된 피드가 없습니다."));
    }

    private void validateTitle(final String title) {
        if (title == null) {
            throw new NotInputException("제목을 입력해주세요.");
        }
    }

    private void validateContent(final String content) {
        if (content == null) {
            throw new NotInputException("내용을 입력해주세요.");
        }
    }
}
