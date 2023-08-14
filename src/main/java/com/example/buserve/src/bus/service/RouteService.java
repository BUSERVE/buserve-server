package com.example.buserve.src.bus.service;

import com.example.buserve.src.bus.DTO.RouteDTO;
import com.example.buserve.src.bus.DTO.SeatDTO;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Seat;
import com.example.buserve.src.bus.entity.Stop;
import com.example.buserve.src.bus.repository.RouteRepository;
import com.example.buserve.src.bus.repository.RouteStopRepository;
import com.example.buserve.src.bus.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final SeatRepository seatRepository;

    public List<Route> searchRoutes(String routeName) {
        return routeRepository.findByRouteName(routeName);
    }

    @Autowired
    public RouteService(RouteRepository routeRepository, RouteStopRepository routeStopRepository, SeatRepository seatRepository) {
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.seatRepository = seatRepository;
    }

    public RouteDTO getRouteInfo(Route routeId, Stop stopId) {
        RouteStop routeStop = routeStopRepository.findByRouteIdAndStopId(routeId, stopId);
        if (routeStop == null) {
            // 처리 방식을 선택: 예외 처리, 메시지 반환 등
        }

        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setId(routeStop.getRoute().getId());
        routeDTO.setExpectedArrivalTime(routeStop.getExpectedArrivalTime());

        List<Seat> seats = seatRepository.findByBus(routeStop.getRoute().getBuses().get(0)); // 첫 번째 버스를 가정
        List<SeatDTO> seatDTOs = seats.stream()
                .map(seat -> new SeatDTO(seat.getSeatNumber(), seat.isAvailable()))
                .collect(Collectors.toList());
        routeDTO.setSeats(seatDTOs);

        return routeDTO;
    }


}