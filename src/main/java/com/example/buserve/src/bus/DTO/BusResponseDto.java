package com.example.buserve.src.bus.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BusResponseDto {
    private Long busId;         // 버스 ID
    @JsonFormat(pattern = "HH:mm")
    private LocalTime expectedArrivalTime; // 도착 예정 시간
    private int remainingSeats; // 남은 좌석 수
    private int totalSeats;     // 총 좌석 수
    private boolean canReserve; // 예약 가능 여부
}
