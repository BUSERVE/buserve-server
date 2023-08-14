package com.example.buserve.src.bus.dto;

import lombok.Data;

@Data
public class SeatDto {
    private final Long id;
    private final Integer seatNumber;
    private final Boolean isAvailable;
}
