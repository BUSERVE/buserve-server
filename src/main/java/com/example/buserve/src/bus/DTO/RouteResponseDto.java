package com.example.buserve.src.bus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteResponseDto {
    private String routeId;
    private String routeName;
}
