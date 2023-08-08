package com.example.buserve.src.pay.controller;

import com.example.buserve.src.pay.dto.ChargingMethodInfoDto;
import com.example.buserve.src.pay.entity.ChargingMethod;
import com.example.buserve.src.pay.service.ChargingMethodService;
import com.example.buserve.src.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/charging-methods")
public class ChargingMethodController {
    private final ChargingMethodService chargingMethodService;

    @Autowired
    public ChargingMethodController(ChargingMethodService chargingMethodService) {
        this.chargingMethodService = chargingMethodService;
    }

    @GetMapping
    public ApiResponse<List<ChargingMethodInfoDto>> getAllChargingMethods(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<ChargingMethod> chargingMethods = chargingMethodService.getAllChargingMethods(userId);
        List<ChargingMethodInfoDto> chargingMethodsDto = chargingMethodService.convertToDto(chargingMethods);
        return ApiResponse.success(chargingMethodsDto);
    }


    @GetMapping("/{method_id}")
    public ApiResponse<ChargingMethodInfoDto> getChargingMethod(Principal principal, @PathVariable Long method_id) {
        Long userId = getUserIdFromPrincipal(principal);
        return ApiResponse.success(chargingMethodService.convertToDto(chargingMethodService.getChargingMethod(userId, method_id)));
    }

    @PostMapping
    public ApiResponse<ChargingMethodInfoDto> addChargingMethod(Principal principal, @RequestBody ChargingMethod chargingMethod) {
        Long userId = getUserIdFromPrincipal(principal);
        ChargingMethod newChargingMethod = chargingMethodService.addChargingMethod(userId, chargingMethod);
        ChargingMethodInfoDto chargingMethodInfoDto = chargingMethodService.convertToDto(newChargingMethod);
        return ApiResponse.success(chargingMethodInfoDto);
    }

    @DeleteMapping("/{method_id}")
    public ApiResponse<List<ChargingMethodInfoDto>> deleteChargingMethod(Principal principal, @PathVariable Long method_id) {
        Long userId = getUserIdFromPrincipal(principal);
        chargingMethodService.deleteChargingMethod(userId, method_id);

        List<ChargingMethod> remainChargingMethods = chargingMethodService.getAllChargingMethods(userId);
        List<ChargingMethodInfoDto> remainChargingMethodsDto = chargingMethodService.convertToDto(remainChargingMethods);
        return ApiResponse.success(remainChargingMethodsDto);
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        // principal에서 사용자 ID를 추출하는 로직
        // 예를 들어, 사용자 이름을 ID로 사용하거나 별도의 서비스를 호출하여 ID를 가져올 수 있습니다.
        // 임시로 UserId 1L 반환
        return 1L;
    }
}
