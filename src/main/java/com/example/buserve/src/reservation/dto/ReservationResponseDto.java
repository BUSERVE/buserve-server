package com.example.buserve.src.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDto {
    private final String routeName; // 노선 이름
    private final String departureStop; // 탑승 정거장 이름
    private final int seatNumber; // 좌석 번호
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime expectedArrivalTime; // 도착 예정 시간
}
