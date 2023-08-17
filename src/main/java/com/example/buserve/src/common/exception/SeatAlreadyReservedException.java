package com.example.buserve.src.common.exception;

public class SeatAlreadyReservedException extends RuntimeException{
    public SeatAlreadyReservedException() {
        super("이미 예약된 좌석입니다.");
    }
}
