package com.example.buserve.src.bus.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String routeName;   // 노선 이름

    @OneToMany(mappedBy = "route")
    private List<Bus> buses = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<RouteStop> routeStops = new ArrayList<>();

    public Route(String routeName) {
        this.routeName = routeName;
    }

    public Route(Long id, String routeName) {
        this.id = id;
        this.routeName = routeName;
    }
}
