package com.example.buserve.src.common.exception;

public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException() {
        super("이미 해당 시간에 예약이 있습니다.");
    }
}
