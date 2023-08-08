package com.likelion.sns.article.comment.domain;

import com.likelion.sns.article.feed.domain.Feed;
import com.likelion.sns.global.entity.BaseEntity;
import com.likelion.sns.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Table(name = "comments")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE comments SET deleted_at = CURRENT_TIMESTAMP where feed_id = ?")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed commentFeedId;

    @Column(name = "deleted_at")
    private LocalDateTime deleteDate;

    @Column(name = "writer")
    private String writer;
    @Column(name = "content")
    private String content;

    public Comment(final String writer, final String content) {
        this.writer = writer;
        this.content = content;
    }

    public void setUser(final User user) {
        this.userId = user;
        user.getComments().add(this);
    }

    public void setFeed(final Feed feed) {
        this.commentFeedId = feed;
        feed.getComments().add(this);
    }

    public void modifyComment(final String content) {
        this.content = content;
    }
}
