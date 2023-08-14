package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
