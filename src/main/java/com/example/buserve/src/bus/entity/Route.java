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
public class Route {
    @Id
    private String id;
    private String routeName;   // 노선 이름

    @OneToMany(mappedBy = "route")
    private List<Bus> buses = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<RouteStop> routeStops = new ArrayList<>();

    public Route(String id, String routeName) {
        this.id = id;
        this.routeName = routeName;
    }
}
