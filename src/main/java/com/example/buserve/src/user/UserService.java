package com.example.buserve.src.user;

import com.example.buserve.src.common.exception.UserNotFoundException;
import com.example.buserve.src.pay.entity.ChargingMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }


        //builder로 user entity 생성 후 db저장
        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();

    }

    public int getBusMoney(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getBusMoney();
    }

    @Transactional
    public void chargeBusMoney(Long userId, int amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        ChargingMethod primaryChargingMethod = user.getPrimaryChargingMethod();
        if (primaryChargingMethod == null) {
            throw new IllegalArgumentException("No primary charging method set for the user.");
        }

        // 이곳에서 주요 충전수단을 사용하여 실제 금액 충전 로직을 구현합니다.
        // 예: paymentService.charge(primaryChargingMethod, amount);

        user.chargeBusMoney(amount);
    }


    @Transactional
    public void useBusMoney(Long userId, int amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.useBusMoney(amount);
    }

    public Long getUserIdByUsername(final String username) {
        return userRepository.findByNickname(username)
                .orElseThrow(UserNotFoundException::new)
                .getId();
    }
}
