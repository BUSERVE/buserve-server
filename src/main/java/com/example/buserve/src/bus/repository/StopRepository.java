package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, String> {
}


