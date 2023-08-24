package com.example.buserve.src.common.exception;

public class FavoriteNotFoundException extends RuntimeException {
    public FavoriteNotFoundException() {
        super("즐겨찾기에 추가된 노선이 없습니다.");
    }
}
