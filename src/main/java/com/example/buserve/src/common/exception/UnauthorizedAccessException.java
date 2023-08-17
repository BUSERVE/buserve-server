package com.example.buserve.src.common.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("접근 권한이 없습니다.");
    }
}
