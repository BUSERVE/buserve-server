package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {
}
