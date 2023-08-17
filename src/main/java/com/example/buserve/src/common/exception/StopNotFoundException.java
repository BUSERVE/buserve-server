package com.example.buserve.src.common.exception;

public class StopNotFoundException extends RuntimeException{
    public StopNotFoundException() {
        super("정류장을 찾을 수 없습니다.");
    }
}
