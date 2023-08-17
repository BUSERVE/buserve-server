package com.example.buserve.src.pay.controller;

import com.example.buserve.src.common.ApiResponseStatus;
import com.example.buserve.src.pay.dto.AddChargingMethodDto;
import com.example.buserve.src.pay.dto.ChargingMethodInfoDto;
import com.example.buserve.src.pay.entity.ChargingMethod;
import com.example.buserve.src.pay.service.ChargingMethodService;
import com.example.buserve.src.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Api(tags = {"충전수단 관련 API"})
@RestController
@RequestMapping("/api/charging-methods")
public class ChargingMethodController {
    private final ChargingMethodService chargingMethodService;

    @Autowired
    public ChargingMethodController(ChargingMethodService chargingMethodService) {
        this.chargingMethodService = chargingMethodService;
    }

    @ApiOperation(value = "사용자의 전체 충전수단 조회 API")
    @GetMapping
    public ApiResponse<List<ChargingMethodInfoDto>> getAllChargingMethods(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<ChargingMethod> chargingMethods = chargingMethodService.getAllChargingMethods(userId);
        List<ChargingMethodInfoDto> chargingMethodsDto = chargingMethodService.convertToDto(chargingMethods);
        return ApiResponse.success(chargingMethodsDto);
    }

    @ApiOperation(value = "충전수단 추가 API")
    @PostMapping
    public ApiResponse<ChargingMethodInfoDto> addChargingMethod(Principal principal, @RequestBody AddChargingMethodDto addChargingMethodDto) {
        Long userId = getUserIdFromPrincipal(principal);

        // DTO에서 ChargingMethod 엔터티를 생성합니다.
        ChargingMethod chargingMethod = ChargingMethod.builder()
                .name(addChargingMethodDto.getName())
                .details(addChargingMethodDto.getDetails())
                .build();

        ChargingMethod newChargingMethod = chargingMethodService.addChargingMethod(userId, chargingMethod);
        ChargingMethodInfoDto chargingMethodInfoDto = chargingMethodService.convertToDto(newChargingMethod);
        return ApiResponse.success(chargingMethodInfoDto);
    }


    @ApiOperation(value = "충전수단 삭제 API")
    @DeleteMapping("/{method_id}")
    public ApiResponse<List<ChargingMethodInfoDto>> deleteChargingMethod(Principal principal, @PathVariable Long method_id) {
        Long userId = getUserIdFromPrincipal(principal);
        chargingMethodService.deleteChargingMethod(userId, method_id);

        List<ChargingMethod> remainChargingMethods = chargingMethodService.getAllChargingMethods(userId);
        List<ChargingMethodInfoDto> remainChargingMethodsDto = chargingMethodService.convertToDto(remainChargingMethods);
        return ApiResponse.success(remainChargingMethodsDto);
    }

    @ApiOperation(value = "주요 충전수단 변경 API", tags = {"충전수단 관련 API"})
    @PutMapping("/primary/{method_id}")
    public ApiResponse<ChargingMethodInfoDto> setPrimaryChargingMethod(Principal principal, @PathVariable Long method_id) {
        Long userId = getUserIdFromPrincipal(principal);
        chargingMethodService.setPrimaryChargingMethod(userId, method_id);

        ChargingMethod primaryChargingMethod = chargingMethodService.getPrimaryChargingMethod(userId);
        ChargingMethodInfoDto primaryChargingMethodInfoDto = chargingMethodService.convertToDto(primaryChargingMethod);
        return ApiResponse.success(primaryChargingMethodInfoDto);
    }

    @ApiOperation(value = "주요 충전수단 조회 API", tags = {"충전수단 관련 API"})
    @GetMapping("/primary")
    public ApiResponse<ChargingMethodInfoDto> getPrimaryChargingMethod(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        ChargingMethod primaryChargingMethod = chargingMethodService.getPrimaryChargingMethod(userId);
        ChargingMethodInfoDto primaryChargingMethodInfoDto = chargingMethodService.convertToDto(primaryChargingMethod);
        return ApiResponse.success(primaryChargingMethodInfoDto);
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        // principal에서 사용자 ID를 추출하는 로직
        // 예를 들어, 사용자 이름을 ID로 사용하거나 별도의 서비스를 호출하여 ID를 가져올 수 있습니다.
        // 임시로 UserId 1L 반환
        return 1L;
    }
}
