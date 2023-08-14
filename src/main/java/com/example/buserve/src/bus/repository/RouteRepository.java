package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByRouteName(String routeName);
}
