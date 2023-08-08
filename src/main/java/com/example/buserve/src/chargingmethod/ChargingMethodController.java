package com.example.buserve.src.chargingmethod;

import com.example.buserve.src.configure.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ApiResponse<List<ChargingMethod>> getAllChargingMethods(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<ChargingMethod> chargingMethods = chargingMethodService.getAllChargingMethods(userId);
        return new ApiResponse<>(chargingMethods);
    }


    @GetMapping("/{method_id}")
    public ResponseEntity<ChargingMethod> getChargingMethod(Principal principal, @PathVariable Long method_id) {
        Long userId = getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(chargingMethodService.getChargingMethod(userId, method_id));
    }

    @PostMapping
    public ResponseEntity<ChargingMethod> addChargingMethod(Principal principal, @RequestBody ChargingMethod chargingMethod) {
        Long userId = getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(chargingMethodService.addChargingMethod(userId, chargingMethod));
    }

    @DeleteMapping("/{method_id}")
    public ResponseEntity<Void> deleteChargingMethod(Principal principal, @PathVariable Long method_id) {
        Long userId = getUserIdFromPrincipal(principal);
        chargingMethodService.deleteChargingMethod(userId, method_id);
        return ResponseEntity.ok().build();
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        // principal에서 사용자 ID를 추출하는 로직
        // 예를 들어, 사용자 이름을 ID로 사용하거나 별도의 서비스를 호출하여 ID를 가져올 수 있습니다.
        // 임시로 UserId 1L 반환
        return 1L;
    }
}
