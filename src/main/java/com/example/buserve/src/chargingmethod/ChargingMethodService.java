package com.example.buserve.src.chargingmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargingMethodService {
    private final ChargingMethodRepository chargingMethodRepository;

    @Autowired
    public ChargingMethodService(ChargingMethodRepository chargingMethodRepository) {
        this.chargingMethodRepository = chargingMethodRepository;
    }

    public List<ChargingMethod> getAllChargingMethods() {
        return chargingMethodRepository.findAll();
    }

    public ChargingMethod getChargingMethod(Long id) {
        return chargingMethodRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Charging method not found"));
    }

    public ChargingMethod addChargingMethod(ChargingMethod chargingMethod) {
        return chargingMethodRepository.save(chargingMethod);
    }

    public void deleteChargingMethod(Long id) {
        chargingMethodRepository.deleteById(id);
    }
}

