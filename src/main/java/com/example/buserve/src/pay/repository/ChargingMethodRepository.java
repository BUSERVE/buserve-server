package com.example.buserve.src.pay.repository;

import com.example.buserve.src.pay.entity.ChargingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChargingMethodRepository extends JpaRepository<ChargingMethod, Long> {
    // 사용자 ID를 기반으로 모든 충전 수단을 찾는 메서드
    List<ChargingMethod> findByUserId(Long userId);

    // 사용자 ID와 충전 수단 ID를 기반으로 특정 충전 수단을 찾는 메서드
    Optional<ChargingMethod> findByUserIdAndId(Long userId, Long id);
}
