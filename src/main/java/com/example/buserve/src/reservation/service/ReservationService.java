package com.example.buserve.src.reservation.service;

import com.example.buserve.src.reservation.dto.ReservationResponseDto;
import com.example.buserve.src.reservation.entity.Reservation;
import com.example.buserve.src.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationResponseDto> getReservations(Long userId) {
        List<Reservation> reservations = reservationRepository.findAllByUserId(userId);
        return reservations.stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getDepartureStop().getRoute().getRouteName(),
                        reservation.getDepartureStop().getStop().getStopName(),
                        reservation.getSeat().getSeatNumber(),
                        reservation.getExpectedArrivalTime()
                ))
                .collect(Collectors.toList());
    }
}
