package com.example.buserve.src.chargingmethod;

import com.example.buserve.src.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ChargingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 충전 수단 이름
    private String details; // 상세 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 충전 수단과 연관된 사용자

    public void setUser(User user) {
        this.user = user;
        user.getChargingMethods().add(this);
    }
}
