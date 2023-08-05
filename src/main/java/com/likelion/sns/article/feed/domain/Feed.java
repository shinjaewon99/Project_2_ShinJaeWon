package com.likelion.sns.article.feed.domain;

import com.likelion.sns.article.articleImage.domain.ArticleImage;
import com.likelion.sns.article.feed.dto.request.CreateFeedRequest;
import com.likelion.sns.article.likeArticle.domain.LikeArticle;
import com.likelion.sns.global.entity.BaseEntity;
import com.likelion.sns.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Table(name = "feeds")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE feeds SET deleted_at = CURRENT_TIMESTAMP where feed_id = ?") // delete 쿼리문을 작성
@Where(clause = "deleted_at is null")
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "draft")
    private Boolean draft;

    @Column(name = "deleted_at")
    private LocalDateTime deleteDate;

    @OneToMany(mappedBy = "imageFeedId")
    private List<ArticleImage> articleImages = new ArrayList<>();

    @OneToMany(mappedBy = "likeFeedId")
    private List<LikeArticle> feedIdLikeArticles = new ArrayList<>();

    public void setUser(User user) {
        this.userId = user;
        user.getFeeds().add(this);
    }

    public void addArticleImage(ArticleImage articleImage) {
        articleImages.add(articleImage);
        articleImage.setFeed(this);
    }

    public void addLikeArticle(LikeArticle likeArticle) {
        feedIdLikeArticles.add(likeArticle);
        likeArticle.setFeed(this);
    }

    public static Feed dtoToEntity(CreateFeedRequest createRequest) {
        Feed feed = new Feed();
        feed.title = createRequest.getTitle();
        feed.content = createRequest.getContent();

        return feed;
    }
}
