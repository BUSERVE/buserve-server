package com.example.buserve.src.user;

import com.example.buserve.src.bus.entity.UserRouteFavorite;
import com.example.buserve.src.pay.entity.ChargingMethod;
import com.example.buserve.src.reservation.entity.Reservation;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USERS")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String socialId;
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
    private SocialType provider; // KAKAO, APPLE, GOOGLE

    private int busMoney; // 버정머니
    private int noShowCount = 0; // 노쇼 횟수
    private LocalDateTime noShowPenaltyDate = null; // 노쇼 페널티 시작날짜

    @OneToOne
    @JoinColumn(name = "primary_charging_method_id")
    private ChargingMethod primaryChargingMethod; // 기본 충전 수단

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChargingMethod> chargingMethods = new ArrayList<>(); // 충전 수단 리스트

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRouteFavorite> favoriteRoutes = new ArrayList<>();

    @Builder
    public User(String socialId, String email, String nickname, Role role, SocialType provider, int busMoney, String imageUrl) {
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
        this.busMoney = busMoney;
        this.noShowCount = 0;
        this.noShowPenaltyDate = null;
        this.chargingMethods = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.imageUrl = imageUrl;
        this.favoriteRoutes = new ArrayList<>();
    }

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

    public void setPrimaryChargingMethod(ChargingMethod chargingMethod) {
        if (this.primaryChargingMethod != null) {
            this.primaryChargingMethod.setPrimary(false);  // 기존 주요 충전수단을 비활성화
        }
        this.primaryChargingMethod = chargingMethod;
        chargingMethod.setPrimary(true);  // 새로운 주요 충전수단을 활성화
    }


    public ChargingMethod getPrimaryChargingMethod() {
        return this.primaryChargingMethod;
    }

    public void removePrimaryChargingMethod() {
        if (this.primaryChargingMethod != null) {
            this.primaryChargingMethod.setPrimary(false);
            this.primaryChargingMethod = null;
        }

        // 다른 충전수단들 중 하나를 주요 충전수단으로 설정
        if (!this.chargingMethods.isEmpty()) {
            ChargingMethod newPrimary = this.chargingMethods.get(this.chargingMethods.size() - 1); // 가장 최근에 추가된 충전수단을 가져옵니다.
            this.setPrimaryChargingMethod(newPrimary);
        }
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void addFavoriteRoute(final UserRouteFavorite favorite) {
        this.favoriteRoutes.add(favorite);
    }
}
