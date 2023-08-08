package com.likelion.sns.userAdditional.domain.userFollows;

import com.likelion.sns.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follows_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User following;

    public void setFollower(final User user) {
        this.follower = user;
        user.getFollows().add(this);
    }
}
