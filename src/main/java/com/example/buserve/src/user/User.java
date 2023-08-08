package com.example.buserve.src.user;

import com.example.buserve.src.pay.entity.ChargingMethod;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "USERS")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

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

    private int busMoney; // 버정머니

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChargingMethod> chargingMethods = new ArrayList<>(); // 충전 수단 리스트

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }


    // 버정머니 충전 메서드
    public void chargeBusMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.busMoney += amount;
    }
    
    // 버정머니 차감 메서드
    public void useBusMoney(int amount) {
        if (amount < 0 || this.busMoney < amount) {
            throw new IllegalArgumentException("amount must be positive and less than busMoney");
        }
        this.busMoney -= amount;
    }
}
