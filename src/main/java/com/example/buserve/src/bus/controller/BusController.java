package com.example.buserve.src.bus.controller;

import com.example.buserve.src.common.ApiResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"버스 관련 API"})
@RestController
@RequestMapping("/api/buses")
public class BusController {

    // 버스 좌석 리스트 조회
    @GetMapping("/buses/{bus_id}/seats")
    public ApiResponse<?> getSeatsForBus(@PathVariable Long bus_id) {
        return ApiResponse.successWithNoContent();
    }
}
