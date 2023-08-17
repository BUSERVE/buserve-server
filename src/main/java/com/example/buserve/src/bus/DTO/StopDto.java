package com.example.buserve.src.bus.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopDto {
    String stopName;
    String stopNumber;
}
