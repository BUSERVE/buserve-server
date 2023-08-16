package com.example.buserve.src.reservation.entity;

public enum AttendanceStatus {
    PENDING,    // 버스가 아직 안 온 상태 (대기중)
    ATTENDED,   // 탑승 완료
    NO_SHOW     // 노쇼
}
