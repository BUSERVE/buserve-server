package com.example.buserve.src.reservation.entity;

import com.example.buserve.src.bus.entity.Bus;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Seat;
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
    private User user;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;  // 예약 좌석

    @ManyToOne
    @JoinColumn(name = "departure_stop_id")
    private RouteStop departureStop;    // 출발 정류장

    private LocalDateTime expectedArrivalTime;  // 도착 예정 시간

    public Reservation(User user, Bus bus, Seat seat, RouteStop departureStop, LocalDateTime expectedArrivalTime) {
        this.user = user;
        this.bus = bus;
        this.seat = seat;
        this.departureStop = departureStop;
        this.expectedArrivalTime = expectedArrivalTime;
    }
}