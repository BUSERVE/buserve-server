package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {
}
