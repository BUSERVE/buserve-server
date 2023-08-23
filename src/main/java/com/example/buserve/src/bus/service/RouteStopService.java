package com.example.buserve.src.bus.service;

import com.example.buserve.src.bus.DTO.RouteDto;
import com.example.buserve.src.bus.DTO.SeatDto;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Seat;
import com.example.buserve.src.bus.entity.Stop;
import com.example.buserve.src.bus.repository.RouteRepository;
import com.example.buserve.src.bus.repository.RouteStopRepository;
import com.example.buserve.src.bus.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteStopService {
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public RouteStopService(RouteRepository routeRepository, RouteStopRepository routeStopRepository, SeatRepository seatRepository) {
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.seatRepository = seatRepository;
    }

    public RouteDto getRouteInfo(String routeId, String stopId) {
        return getRouteDto(routeStopRepository.findByRouteIdAndStopId(routeId, stopId), seatRepository, routeId, stopId);
    }

    static RouteDto getRouteDto(RouteStop byRouteIdAndStopId, SeatRepository seatRepository, String routeId, String stopId) {
        RouteStop routeStop = byRouteIdAndStopId;
        if (routeStop == null) {
            // 처리 방식을 선택: 예외 처리, 메시지 반환 등
        }

        RouteDto routeDTO = new RouteDto();
        routeDTO.setId(routeStop.getRoute().getId());
        routeDTO.setExpectedArrivalTime(routeStop.getExpectedArrivalTime());

        List<Seat> seats = seatRepository.findByBus(routeStop.getRoute().getBuses().get(0)); // 첫 번째 버스를 가정
        List<SeatDto> seatDTOs = seats.stream()
                .map(seat -> new SeatDto(seat.getSeatNumber(), seat.isAvailable()))
                .collect(Collectors.toList());
        routeDTO.setSeats(seatDTOs);

        return routeDTO;
    }


}
