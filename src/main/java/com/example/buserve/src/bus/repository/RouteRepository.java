package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, String> {
    Route findByRouteName(String routeName);
    List<Route> findAllByRouteName(String routeName);

    @Query("SELECT r.id FROM Route r")
    List<String> findAllRouteId();

    List<Route> findAllByRouteNameLike(String routeName);
}
