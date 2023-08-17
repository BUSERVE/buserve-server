package com.example.buserve.src.common.exception;

public class InsufficientBusMoneyException extends RuntimeException {
    public InsufficientBusMoneyException() {
        super("버정머니가 부족합니다.");
    }
}
