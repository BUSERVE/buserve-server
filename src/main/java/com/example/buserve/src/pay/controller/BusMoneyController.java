package com.example.buserve.src.pay.controller;

import com.example.buserve.src.common.ApiResponseStatus;
import com.example.buserve.src.pay.dto.AmountDto;
import com.example.buserve.src.common.ApiResponse;
import com.example.buserve.src.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(tags = {"버스머니 관련 API"})
@RestController
@RequestMapping("/api/bus-money")
public class BusMoneyController {
    private final UserService userService;

    @Autowired
    public BusMoneyController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "사용자의 버스머니 조회 API")
    @GetMapping
    public ApiResponse<AmountDto> getBusMoney(String name) {
        final Long userId = userService.getUserIdByUsername(name);
//        Long userId = getUserIdFromPrincipal(principal); // 현재 로그인한 사용자 ID 획득
        int currentBusMoney = userService.getBusMoney(userId);
        return ApiResponse.success(new AmountDto(currentBusMoney));
    }

    @ApiOperation(value = "사용자의 버스머니 충전 API")
    @PostMapping("/charge")
    public ApiResponse<AmountDto> chargeBusMoney(String name, @RequestBody AmountDto amountDto) {
        Long userId = userService.getUserIdByUsername(name);
//        Long userId = getUserIdFromPrincipal(principal);
        userService.chargeBusMoney(userId, amountDto.getAmount());
        int changedMoney = userService.getBusMoney(userId);
        return ApiResponse.success(new AmountDto(changedMoney));
    }

    // 로그인한 사용자의 ID를 획득하는 메서드 (구현 필요)
    private Long getUserIdFromPrincipal(Principal principal) {
        // 로그인한 사용자의 ID를 반환하는 로직
        // 예: return ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();

        // 임시로 사용자 ID 1L 반환
        return 1L;
    }
}

