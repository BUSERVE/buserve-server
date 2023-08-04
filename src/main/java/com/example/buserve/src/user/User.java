package com.example.buserve.src.user;

import com.example.buserve.src.chargingmethod.ChargingMethod;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
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

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String nickname; // 닉네임

    private String imageUrl; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, APPLE, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)
    private String refreshToken; // 리프레시 토큰

    private int busMoney; // 버정머니

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChargingMethod> chargingMethods = new ArrayList<>(); // 충전 수단 리스트

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }


    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
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
