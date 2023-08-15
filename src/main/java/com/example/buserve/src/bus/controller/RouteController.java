package com.example.buserve.src.bus.controller;

import com.example.buserve.src.bus.DTO.RouteDto;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.Stop;
import com.example.buserve.src.bus.repository.RouteRepository;
import com.example.buserve.src.bus.service.RouteService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return routeService.searchRoutes(routeName);
    }


//    @PostMapping("/saveRoute")
//    public ResponseEntity<String> saveRoute() {
//        Route route = new Route("0000");
//        routeService.saveRoute(route);
//        return ResponseEntity.ok("Route saved");
//    }
//    @Autowired
//    private RouteRepository routeRepository;
//    @GetMapping("/routes")
//    public ResponseEntity<List<Route>> getAllRoutes() {
//        List<Route> routes = routeRepository.findAll();
//        return ResponseEntity.ok(routes);
//    }

    @GetMapping("/{route_id}/stops/{stop_id}/buses")
    public RouteDto getRouteInfo(
            @PathVariable("route_id") Route routeId,
            @PathVariable("stop_id") Stop stopId) {
        return routeService.getRouteInfo(routeId, stopId);
    }

}
