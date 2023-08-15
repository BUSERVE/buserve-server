package com.example.buserve.src.bus.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stopName;    // 정류장 이름
    private int stopNumber;  // 정류장 번호

    @OneToMany(mappedBy = "stop")
    private List<RouteStop> routeStops = new ArrayList<>();

    public Stop(String stopName, int stopNumber) {
        this.stopName = stopName;
        this.stopNumber = stopNumber;
    }
}
