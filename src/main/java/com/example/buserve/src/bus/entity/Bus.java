package com.example.buserve.src.bus.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime departureTime;  // 출발 시간
    private int totalSeats; // 총 좌석 수

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToMany(mappedBy = "bus")
    private List<Seat> seats = new ArrayList<>();

    public Bus(int totalSeats, LocalTime departureTime, Route route) {
        this.totalSeats = totalSeats;
        this.departureTime = departureTime;
        this.route = route;
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new Seat(i, this));
        }
    }
}