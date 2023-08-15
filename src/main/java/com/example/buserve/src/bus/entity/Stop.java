package com.example.buserve.src.bus.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stopName;    // 정류장 이름
    private String stopNumber;  // 정류장 번호

    @OneToMany(mappedBy = "stop")
    private List<RouteStop> routeStops = new ArrayList<>();

    public Stop(String stopName, String stopNumber) {
        this.stopName = stopName;
        this.stopNumber = stopNumber;
    }
}
