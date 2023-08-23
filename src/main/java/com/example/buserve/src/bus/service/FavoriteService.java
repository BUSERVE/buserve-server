package com.example.buserve.src.bus.service;

import com.example.buserve.src.bus.DTO.RouteResponseDto;
import com.example.buserve.src.bus.entity.Direction;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.UserRouteFavorite;
import com.example.buserve.src.bus.repository.RouteRepository;
import com.example.buserve.src.bus.repository.UserRouteFavoriteRepository;
import com.example.buserve.src.common.exception.RouteNotFoundException;
import com.example.buserve.src.common.exception.UserNotFoundException;
import com.example.buserve.src.user.User;
import com.example.buserve.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final UserRouteFavoriteRepository userRouteFavoriteRepository;

    public void removeRouteFromFavorite(final String routeId, final String name) {
        final User user = userRepository.findByNickname(name).orElseThrow(UserNotFoundException::new);
        final Route route = routeRepository.findById(routeId).orElseThrow(RouteNotFoundException::new);

        final UserRouteFavorite favorite = userRouteFavoriteRepository.findByUserAndRoute(user, route);

        userRouteFavoriteRepository.delete(favorite);
    }

    public List<RouteResponseDto> getFavoritesByUsername(final String name) {
        final User user = userRepository.findByNickname(name).orElseThrow(UserNotFoundException::new);
        final List<UserRouteFavorite> favorites = userRouteFavoriteRepository.findAllByUser(user);

        return favorites.stream()
                .map(favorite -> {
                    final Route route = favorite.getRoute();
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
                            true);
                })
                .collect(Collectors.toList());
    }

    public void addRoute(final String routeId, final String name) {
        final User user = userRepository.findByNickname(name).orElseThrow(UserNotFoundException::new);
        final Route route = routeRepository.findById(routeId).orElseThrow(RouteNotFoundException::new);
        final UserRouteFavorite favorite = new UserRouteFavorite();
        favorite.setUserAndRoute(user, route);
        userRouteFavoriteRepository.save(favorite);
    }
}
