package com.example.buserve.src.bus.service;


import com.example.buserve.src.bus.DTO.StopDto;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.repository.RouteRepository;
import com.example.buserve.src.bus.repository.RouteStopRepository;
import com.example.buserve.src.bus.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository, RouteStopRepository routeStopRepository, SeatRepository seatRepository) {
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.seatRepository = seatRepository;
    }

    public List<Route> searchRoutes(String routeName) {
        return routeRepository.findAllByRouteName(routeName);
    }


    public List<StopDto> getStopsByRoute(String routeName) {
        Route route = routeRepository.findByRouteName(routeName);
        if (route == null) {
            return null;
        }

        List<StopDto> stops = route.getRouteStops().stream()
                .map(routeStop -> new StopDto(routeStop.getStop().getStopName(), routeStop.getStop().getStopNumber()))
                .collect(Collectors.toList());
        return stops;
    }

}
