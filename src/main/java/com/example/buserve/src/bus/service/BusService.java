package com.example.buserve.src.bus.service;

import com.example.buserve.src.bus.DTO.BusResponseDto;
import com.example.buserve.src.bus.DTO.SeatDto;
import com.example.buserve.src.bus.entity.Bus;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Seat;
import com.example.buserve.src.bus.repository.BusRepository;
import com.example.buserve.src.bus.repository.RouteStopRepository;
import com.example.buserve.src.bus.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusService {

    private final SeatRepository seatRepository;
    private final BusRepository busRepository;
    private final RouteStopRepository routeStopRepository;

    public List<SeatDto> getSeatsForBus(Long busId) {
        List<Seat> seats = seatRepository.findByBusId(busId);
        return seats.stream()
                .map(seat -> new SeatDto( seat.getSeatNumber(), seat.isAvailable()))
                .collect(Collectors.toList());
    }

    public List<BusResponseDto> getBusesByRouteAndStop(String routeId, String stopId) {
        List<Bus> buses = busRepository.findAllByRouteId(routeId);
        RouteStop routeStop = routeStopRepository.findByRouteIdAndStopId(routeId, stopId);
        return buses.stream()
                .map(bus ->
                {
                    int remainingSeats = (int) bus.getSeats().stream().filter(Seat::isAvailable).count();
                    return new BusResponseDto(
                            bus.getId(),
                            calculateExpectedArrivalTime(bus, routeStop),
                            remainingSeats,
                            bus.getTotalSeats(),
                            remainingSeats > 0
                    );
                })
                .collect(Collectors.toList());
    }

    private LocalTime calculateExpectedArrivalTime(Bus bus, RouteStop routeStop) {
        return bus.getDepartureTime().plusMinutes(5L * routeStop.getSequence());
    }
}
