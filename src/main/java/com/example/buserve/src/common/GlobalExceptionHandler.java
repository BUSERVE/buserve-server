package com.example.buserve.src.common;

import com.example.buserve.src.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ApiResponseStatus.USER_NOT_FOUND));
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleSeatNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ApiResponseStatus.SEAT_NOT_FOUND));
    }

    @ExceptionHandler(SeatAlreadyReservedException.class)
    public ResponseEntity<ApiResponse<?>> handleSeatAlreadyReservedException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ApiResponseStatus.SEAT_ALREADY_RESERVED));
    }

    @ExceptionHandler(StopNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleStopNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ApiResponseStatus.STOP_NOT_FOUND));
    }

    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateReservationException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(ApiResponseStatus.DUPLICATE_RESERVATION));
    }

    @ExceptionHandler(ReservationRestrictionException.class)
    public ResponseEntity<ApiResponse<?>> handleReservationRestrictionException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(ApiResponseStatus.RESERVATION_RESTRICTED));
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleReservationNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ApiResponseStatus.RESERVATION_NOT_FOUND));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorizedAccessException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(ApiResponseStatus.UNAUTHORIZED_ACCESS));
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ApiResponseStatus.REQUEST_ERROR));
    }
}
