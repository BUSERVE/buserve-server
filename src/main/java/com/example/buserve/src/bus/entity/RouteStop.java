package com.example.buserve.src.bus.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "stop_id")
    private Stop stop;

    private int sequence;   // 순번

    private LocalTime expectedArrivalTime;  // 예상 도착 시간

    @Enumerated(EnumType.STRING)
    private Direction direction;    // 상행, 하행

    public RouteStop(Route route, Stop stop, int sequence, LocalTime expectedArrivalTime, Direction direction) {
        this.route = route;
        this.stop = stop;
        this.sequence = sequence;
        this.expectedArrivalTime = expectedArrivalTime;
        this.direction = direction;
    }
}
