package com.example.buserve.src.reservation.entity;

import com.example.buserve.src.bus.entity.Bus;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Seat;
import com.example.buserve.src.bus.entity.Stop;
import com.example.buserve.src.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 예약자

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;  // 예약 좌석

    @ManyToOne
    @JoinColumn(name = "stop_id")
    private Stop stop;    // 출발 정류장

    private LocalDateTime expectedArrivalTime;  // 도착 예정 시간

    public Reservation(User user, Seat seat, Stop stop, LocalDateTime expectedArrivalTime) {
        this.user = user;
        this.seat = seat;
        this.stop = stop;
        this.expectedArrivalTime = expectedArrivalTime;
    }
}