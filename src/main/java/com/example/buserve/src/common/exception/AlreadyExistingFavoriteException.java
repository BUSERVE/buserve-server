package com.example.buserve.src.common.exception;

public class AlreadyExistingFavoriteException extends RuntimeException {
    public AlreadyExistingFavoriteException() {
        super("이미 즐겨찾기에 추가된 노선입니다.");
    }
}
