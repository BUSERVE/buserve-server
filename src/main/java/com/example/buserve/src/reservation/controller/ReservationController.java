package com.example.buserve.src.reservation.controller;

import com.example.buserve.src.common.ApiResponse;
import com.example.buserve.src.reservation.dto.ReservationResponseDto;
import com.example.buserve.src.reservation.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Api(tags = {"예약 관련 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation(value = "예약 내역 조회", notes = "사용자의 예약 내역을 최근순으로 조회한다.")
    @GetMapping
    public ApiResponse<List<ReservationResponseDto>> getReservations(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return ApiResponse.success(reservationService.getReservations(userId));
    }

    // 로그인한 사용자의 ID를 획득하는 메서드 (구현 필요)
    private Long getUserIdFromPrincipal(Principal principal) {
        // 로그인한 사용자의 ID를 반환하는 로직
        // 예: return ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();

        // 임시로 사용자 ID 1L 반환
        return 1L;
    }
}
