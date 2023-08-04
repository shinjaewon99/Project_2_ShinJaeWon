package com.likelion.sns.article.articleImage.domain;

import com.likelion.sns.article.feed.domain.Feed;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "article_image")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feedId;

    private String imageUrl;
}
