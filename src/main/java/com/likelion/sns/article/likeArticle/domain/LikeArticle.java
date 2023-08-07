package com.likelion.sns.article.likeArticle.domain;

import com.likelion.sns.article.feed.domain.Feed;
import com.likelion.sns.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "like_article")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed likeFeedId;

    public LikeArticle(final User user, final Feed feed) {
        this.userId = user;
        this.likeFeedId = feed;
        user.getUserLikeArticles().add(this);
        feed.getFeedIdLikeArticles().add(this);
    }
}
