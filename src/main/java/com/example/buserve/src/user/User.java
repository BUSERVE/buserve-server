package com.example.buserve.src.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "USERS")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name ="EMAIL", nullable = false, unique = true)
    private String email; // 이메일

    @Column(name = "NICKNAME",nullable = false)
    private String nickname; // 닉네임

    @Column(name = "PROFILE_IMAGE_URL")
    private String imageUrl; // 프로필 이미지

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "SOCIALTYPE")
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, APPLE, GOOGLE

    public User(@NotNull String email,
                @NotNull String nickname,
                @NotNull String imageUrl,
                @NotNull Role role,
                @NotNull SocialType socialType)
    {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.role = role;
        this.socialType = socialType;
    }

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

}
