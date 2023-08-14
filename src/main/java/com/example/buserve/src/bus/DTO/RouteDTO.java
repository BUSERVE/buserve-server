package com.example.buserve.src.bus.DTO;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDTO {
    private Long id;
    private LocalTime expectedArrivalTime;
    private List<SeatDTO> seats;
}