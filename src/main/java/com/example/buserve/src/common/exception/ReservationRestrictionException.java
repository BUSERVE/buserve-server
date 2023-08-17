package com.example.buserve.src.common.exception;

public class ReservationRestrictionException extends RuntimeException {
    public ReservationRestrictionException() {
        super("노쇼로 인한 예약 제한 중입니다.");
    }
}
