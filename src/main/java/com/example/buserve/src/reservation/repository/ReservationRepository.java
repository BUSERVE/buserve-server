package com.example.buserve.src.reservation.repository;

import com.example.buserve.src.bus.entity.Seat;
import com.example.buserve.src.reservation.entity.BoardingStatus;
import com.example.buserve.src.reservation.entity.Reservation;
import com.example.buserve.src.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserId(Long userId);

    List<Reservation> findAllByUserAndExpectedArrivalTime(User user, LocalDateTime expectedArrivalTime);

    List<Reservation> findByBoardingStatus(BoardingStatus boardingStatus);
}
