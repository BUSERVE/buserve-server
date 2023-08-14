package com.example.buserve.src.bus.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDTO {
    private int seatNumber;
    private boolean isAvailable;
}
