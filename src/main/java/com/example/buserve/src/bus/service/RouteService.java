package com.example.buserve.src.bus.service;


import com.example.buserve.src.bus.DTO.BusResponseDto;
import com.example.buserve.src.bus.DTO.RouteDto;
import com.example.buserve.src.bus.DTO.RouteResponseDto;
import com.example.buserve.src.bus.DTO.StopDto;
import com.example.buserve.src.bus.entity.Bus;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Stop;
import com.example.buserve.src.bus.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    private final RouteStopRepository routeStopRepository;

    public List<RouteResponseDto> searchRoutes(String routeName) {
        String searchRouteName = "%" + routeName + "%";
        List<Route> routes = routeRepository.findAllByRouteNameLike(searchRouteName);
        return routes.stream()
                .map(route -> new RouteResponseDto(route.getId(), route.getRouteName()))
                .collect(Collectors.toList());
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

    public List<String> getAllRouteId() {
        return routeRepository.findAllRouteId();
    }

    public List<RouteResponseDto> getNearbyRoutes(final double lat, final double lon) {
        final List<Stop> nearStops = stopRepository.findWithinDistance(lat, lon, 500);

        // 중복된 노선을 제거하기 위한 Set 생성
        Set<Route> uniqueRoutes = new HashSet<>();

        // 각 정류장에 연결된 노선들을 Set에 추가
        for (Stop stop : nearStops) {
            for (RouteStop routeStop : stop.getRouteStops()) {
                uniqueRoutes.add(routeStop.getRoute());
            }
        }

        // Set의 각 노선을 RouteDto로 변환하여 List로 반환
        return uniqueRoutes.stream()
                .map(route -> new RouteResponseDto(
                        route.getId(),
                        route.getRouteName()))
                .collect(Collectors.toList());
    }

}
