package com.example.buserve.src.bus.service;


import com.example.buserve.src.bus.DTO.*;
import com.example.buserve.src.bus.entity.*;
import com.example.buserve.src.bus.repository.*;
import com.example.buserve.src.common.exception.UserNotFoundException;
import com.example.buserve.src.user.User;
import com.example.buserve.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    private final UserRepository userRepository;

    public List<RouteResponseDto> searchRoutes(String routeName, final String name) {
        final User user = userRepository.findByNickname(name).orElseThrow(UserNotFoundException::new);
        String searchRouteName = "%" + routeName + "%";
        List<Route> routes = routeRepository.findAllByRouteNameLike(searchRouteName);
        return routes.stream()
                .map(route -> convertToRouteResponseDto(route, user))
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

    public List<RouteResponseDto> getNearbyRoutes(final double lat, final double lon, final String name) {
        final User user = userRepository.findByNickname(name).orElseThrow(UserNotFoundException::new);
        final List<Stop> nearStops = stopRepository.findWithinDistance(lat, lon, 2000);

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
                .map(route -> convertToRouteResponseDto(route, user))
                .collect(Collectors.toList());
    }

    public List<StopResponseDto> getStopsByRouteId(final String routeId) {
        final Route route = routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("해당 노선이 존재하지 않습니다."));
        return route.getRouteStops().stream()
                .filter(routeStop -> routeStop.getDirection() == Direction.UPWARD)
                .map(routeStop -> new StopResponseDto(
                        routeStop.getStop().getId(),
                        routeStop.getStop().getStopName(),
                        routeStop.getStop().getStopNumber()
                )).collect(Collectors.toList());
    }

    private RouteResponseDto convertToRouteResponseDto(final Route route, final User user) {
        final List<RouteStop> routeStops = route.getRouteStops();
        final List<RouteStop> upwardRouteStops = routeStops.stream()
                .filter(rs -> rs.getDirection() == Direction.UPWARD)
                .sorted(Comparator.comparingInt(RouteStop::getSequence))
                .collect(Collectors.toList());
        return new RouteResponseDto(
                route.getId(),
                route.getRouteName(),
                upwardRouteStops.get(0).getStop().getStopName(),
                upwardRouteStops.get(upwardRouteStops.size() - 1).getStop().getStopName(),
                isFavorite(route, user));
    }

    private boolean isFavorite(final Route route, final User user) {
        return user.getFavoriteRoutes().stream()
                .anyMatch(favorite -> favorite.getRoute().equals(route));
    }
}
