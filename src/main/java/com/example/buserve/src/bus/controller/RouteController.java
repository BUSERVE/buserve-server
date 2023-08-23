package com.example.buserve.src.bus.controller;

import com.example.buserve.src.bus.DTO.BusResponseDto;
import com.example.buserve.src.bus.DTO.RouteDto;
import com.example.buserve.src.bus.DTO.RouteResponseDto;
import com.example.buserve.src.bus.DTO.StopDto;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.service.BusService;
import com.example.buserve.src.bus.service.RouteService;
import com.example.buserve.src.common.ApiResponse;
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
    private final BusService busService;


    @Autowired
    public RouteController(RouteService routeService, BusService busService){
        this.routeService = routeService;
        this.busService = busService;
    }


    @ApiOperation(value = "버스 노선 목록 조회")
    @GetMapping("/search")
    public ApiResponse<List<RouteResponseDto>> searchRoutes(@RequestParam(required = false, defaultValue = "") String routeName){
        return ApiResponse.success(routeService.searchRoutes(routeName));
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

    @ApiOperation(value = "버스 목록 조회", notes = "특정 버스 노선의 해당 정류장에서의 버스 목록을 조회합니다.")
    @GetMapping("/{route_id}/stops/{stop_id}")
    public ApiResponse<List<BusResponseDto>> getBusesByRouteAndStop(@PathVariable("route_id") String routeId, @PathVariable("stop_id") String stopId) {
        List<BusResponseDto> buses = busService.getBusesByRouteAndStop(routeId, stopId);
        return ApiResponse.success(buses);
    }

    @ApiOperation(value = "주변 노선 조회", notes = "특정 위치에서 반경 500M의 버스 정류장을 지나는 노선 목록을 조회합니다.")
    @GetMapping("/nearby")
    public ApiResponse<List<RouteResponseDto>> getNearbyRoutes(@RequestParam double lat, @RequestParam double lon) {
        List<RouteResponseDto> routes = routeService.getNearbyRoutes(lat, lon);
        return ApiResponse.success(routes);
    }

}
