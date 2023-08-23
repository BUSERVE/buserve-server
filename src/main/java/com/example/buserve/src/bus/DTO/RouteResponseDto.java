package com.example.buserve.src.bus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteResponseDto {
    private String routeId;
    private String routeName;
    private String startPoint; // 기점
    private String endPoint;   // 종점
    private boolean isFavorite; // 즐겨찾기 여부
}
