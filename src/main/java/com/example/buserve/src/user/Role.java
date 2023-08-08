package com.example.buserve.src.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반사용자권한"),
    ADMIN( "ROLE_ADMIN", "관리자권한"),
    GUEST("ROLE_GUEST", "게스트권한");

    private final String code;
    private final String displayName;

    public static Role of(String code) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(GUEST);
    }

}
