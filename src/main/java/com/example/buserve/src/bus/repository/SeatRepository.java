package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByBusId(Long busId);
}
