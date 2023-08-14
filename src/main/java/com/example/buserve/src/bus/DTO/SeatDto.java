package com.example.buserve.src.bus.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDto {
    private int seatNumber;
    private boolean isAvailable;

}

