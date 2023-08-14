package com.example.buserve.src.bus.controller;

import com.example.buserve.src.bus.DTO.RouteDTO;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.RouteStop;
import com.example.buserve.src.bus.entity.Stop;
import com.example.buserve.src.bus.service.RouteService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"버스 노선 관련 API"})
@RestController
@RequestMapping("/api/routes")
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }

    @GetMapping
    public List<Route> searchRoutes(@RequestParam String routeName){
        List<Route> route = routeService.searchRoutes(routeName);
        return route;
    }

    @GetMapping("/{route_id}/stops/{stop_id}/buses")
    public RouteDTO getRouteInfo(
            @PathVariable("route_id") Route routeId,
            @PathVariable("stop_id") Stop stopId) {
        return routeService.getRouteInfo(routeId, stopId);
    }

}
