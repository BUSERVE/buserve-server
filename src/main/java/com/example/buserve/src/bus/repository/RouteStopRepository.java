package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {
    RouteStop findByRouteIdAndStopId(Route routeId, Stop stopId);
}
