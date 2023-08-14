package com.example.buserve.src.bus.service;

import com.example.buserve.src.bus.entity.Seat;
import com.example.buserve.src.bus.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusService {

    private final SeatRepository seatRepository;

    @Autowired
    public BusService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatDto> getSeatsForBus(Long busId) {
        List<Seat> seats = seatRepository.findByBusId(busId);
        return seats.stream()
                .map(seat -> new SeatDto(seat.getId(), seat.getSeatNumber(), seat.isAvailable()))
                .collect(Collectors.toList());
    }
}
