package com.likelion.sns.user.domain;

import com.likelion.sns.article.feed.domain.Feed;
import com.likelion.sns.article.likeArticle.domain.LikeArticle;
import com.likelion.sns.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자의 접근 제어자를 작성하여 무분별한 생성자를 막는다.
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_img")
    private String profileImg;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<LikeArticle> userLikeArticles = new ArrayList<>();

    public User(final String username, final String password, final String email,
                final String phone, final UserRole role) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.userRole = role;
    }

    public void addFeed(final Feed feed) {
        feeds.add(feed);
        feed.setUser(this);
    }

    public void uploadImg(final String imgUrl) {
        this.profileImg = imgUrl;
    }

    // 사용자에게 부여된 권한을 지정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // 계정이 만료되지 않았는지 검증
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정이 잠겨있는지 검증
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자 인증정보가 만료되었는지 검증
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 되었는지 검증
    @Override
    public boolean isEnabled() {
        return true;
    }
}
