package com.example.buserve.src.chargingmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ChargingMethod>> getAllChargingMethods() {
        return ResponseEntity.ok(chargingMethodService.getAllChargingMethods());
    }

    @GetMapping("/{method_id}")
    public ResponseEntity<ChargingMethod> getChargingMethod(@PathVariable Long method_id) {
        return ResponseEntity.ok(chargingMethodService.getChargingMethod(method_id));
    }

    @PostMapping
    public ResponseEntity<ChargingMethod> addChargingMethod(@RequestBody ChargingMethod chargingMethod) {
        return ResponseEntity.ok(chargingMethodService.addChargingMethod(chargingMethod));
    }

    @DeleteMapping("/{method_id}")
    public ResponseEntity<Void> deleteChargingMethod(@PathVariable Long method_id) {
        chargingMethodService.deleteChargingMethod(method_id);
        return ResponseEntity.ok().build();
    }
}
