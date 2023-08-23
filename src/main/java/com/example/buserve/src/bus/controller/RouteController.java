package com.example.buserve.src.bus.controller;

import com.example.buserve.src.bus.DTO.*;
import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.service.BusService;
import com.example.buserve.src.bus.service.FavoriteService;
import com.example.buserve.src.bus.service.RouteService;
import com.example.buserve.src.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"버스 노선 관련 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RouteController {
    private final RouteService routeService;
    private final BusService busService;
    private final FavoriteService favoriteService;

    @ApiOperation(value = "버스 노선 목록 조회")
    @GetMapping("/search")
    public ApiResponse<List<RouteResponseDto>> searchRoutes(String name, @RequestParam(required = false, defaultValue = "") String routeName){
        return ApiResponse.success(routeService.searchRoutes(routeName, name));
    }

    @ApiOperation(value = "버스 정류장 조회")
    @GetMapping("/{route_id}/stops")
    public ApiResponse<List<StopResponseDto>> getStopsByRouteName(@PathVariable("route_id") String routeId){
        List<StopResponseDto> stops = routeService.getStopsByRouteId(routeId);
        return ApiResponse.success(stops);
    }

    @ApiOperation(value = "버스 목록 조회", notes = "특정 버스 노선의 해당 정류장에서의 버스 목록을 조회합니다.")
    @GetMapping("/{route_id}/stops/{stop_id}")
    public ApiResponse<List<BusResponseDto>> getBusesByRouteAndStop(@PathVariable("route_id") String routeId, @PathVariable("stop_id") String stopId) {
        List<BusResponseDto> buses = busService.getBusesByRouteAndStop(routeId, stopId);
        return ApiResponse.success(buses);
    }

    @ApiOperation(value = "주변 노선 조회", notes = "특정 위치에서 반경 3KM의 버스 정류장을 지나는 노선 목록을 조회합니다.")
    @GetMapping("/nearby")
    public ApiResponse<List<RouteResponseDto>> getNearbyRoutes(String name, @RequestParam double lat, @RequestParam double lon) {
        List<RouteResponseDto> routes = routeService.getNearbyRoutes(lat, lon, name);
        return ApiResponse.success(routes);
    }

    @ApiOperation(value = "즐겨찾기 노선 추가", notes = "사용자가 즐겨찾기할 노선을 추가합니다.", tags = {"즐겨찾기 관련 API"})
    @PostMapping("/{route_id}/favorite")
    public ApiResponse<Void> addRouteToFavorite(String name, @PathVariable(name="route_id") String routeId) {
        favoriteService.addRoute(routeId, name);
        return ApiResponse.successWithNoContent();
    }

    @ApiOperation(value = "즐겨찾기 노선 조회", notes = "사용자가 즐겨찾기한 노선 목록을 조회합니다.", tags = {"즐겨찾기 관련 API"})
    @GetMapping("/favorites")
    public ApiResponse<List<RouteResponseDto>> getFavoriteRoutes(String name) {
        return ApiResponse.success(favoriteService.getFavoritesByUsername(name));
    }

    @ApiOperation(value = "즐겨찾기 노선 삭제", notes = "즐겨찾기 노선을 삭제합니다.", tags = {"즐겨찾기 관련 API"})
    @DeleteMapping("/{route_id}/favorite")
    public ApiResponse<Void> removeRouteFromFavorite(String name, @PathVariable(name="route_id") String routeId) {
        favoriteService.removeRouteFromFavorite(routeId, name);
        return ApiResponse.successWithNoContent();
    }
}
