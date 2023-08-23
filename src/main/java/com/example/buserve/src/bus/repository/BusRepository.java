package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Bus;
import com.example.buserve.src.bus.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findAllByRouteId(String routeId);
}
