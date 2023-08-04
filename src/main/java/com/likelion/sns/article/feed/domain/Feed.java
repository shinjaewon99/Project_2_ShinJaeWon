package com.likelion.sns.article.feed.domain;

import com.likelion.sns.article.likeArticle.domain.LikeArticle;
import com.likelion.sns.global.entity.BaseEntity;
import com.likelion.sns.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "feeds")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "feedId")
    private List<LikeArticle> feedIdLikeArticles = new ArrayList<>();

    public void setUser(User user) {
        this.userId = user;
        user.addFeed(this);
    }
}
