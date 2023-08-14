package com.example.buserve.src.bus.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seatNumber;     // 좌석 번호
    private boolean isAvailable;    // 예약 가능 여부

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    public Seat(int seatNumber, Bus bus) {
        this.seatNumber = seatNumber;
        this.bus = bus;
        this.isAvailable = true;
    }
}