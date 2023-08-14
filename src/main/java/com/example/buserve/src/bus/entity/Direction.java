package com.example.buserve.src.bus.entity;

public enum Direction {
    UPWARD("상행"),
    DOWNWARD("하행");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
