package com.example.buserve.src.chargingmethod;

import com.example.buserve.src.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/bus-money")
public class BusMoneyController {
    private final UserService userService;

    @Autowired
    public BusMoneyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Integer> getBusMoney(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal); // 현재 로그인한 사용자 ID 획득
        return ResponseEntity.ok(userService.getBusMoney(userId));
    }

    @PostMapping("/charge")
    public ResponseEntity<Void> chargeBusMoney(Principal principal, @RequestParam int amount) {
        Long userId = getUserIdFromPrincipal(principal);
        userService.chargeBusMoney(userId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/use")
    public ResponseEntity<Void> useBusMoney(Principal principal, @RequestParam int amount) {
        Long userId = getUserIdFromPrincipal(principal);
        userService.useBusMoney(userId, amount);
        return ResponseEntity.ok().build();
    }

    // 로그인한 사용자의 ID를 획득하는 메서드 (구현 필요)
    private Long getUserIdFromPrincipal(Principal principal) {
        // 로그인한 사용자의 ID를 반환하는 로직
        // 예: return ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();

        // 임시로 사용자 ID 1L 반환
        return 1L;
    }
}

