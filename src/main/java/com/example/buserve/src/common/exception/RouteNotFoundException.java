package com.example.buserve.src.common.exception;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException() {
        super("해당 노선을 찾을 수 없습니다.");
    }
}
