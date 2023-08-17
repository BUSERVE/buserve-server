package com.example.buserve.src.bus.controller;

import com.example.buserve.src.bus.DTO.StopDto;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value = "버스 노선 목록 조회")
    @GetMapping("/search")
    public List<Route> searchRoutes(@RequestParam String routeName){
        return routeService.searchRoutes(routeName);
    }

    @ApiOperation(value = "버스 정류장 조회")
    @GetMapping("/{route_id}/stops")
    public ResponseEntity<List<StopDto>> getStopsByRouteName(@PathVariable("route_id") String routeName){
        List<StopDto> stops = routeService.getStopsByRoute(routeName);
        if (stops != null && !stops.isEmpty()) {
            return ResponseEntity.ok(stops);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
