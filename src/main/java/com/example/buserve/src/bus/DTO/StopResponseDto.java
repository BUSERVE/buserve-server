package com.example.buserve.src.bus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StopResponseDto {
    private String stopId;
    private String stopName;
    private String stopNumber;
}
