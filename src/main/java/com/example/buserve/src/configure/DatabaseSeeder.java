package com.example.buserve.src.configure;

import com.example.buserve.src.pay.entity.ChargingMethod;
import com.example.buserve.src.pay.repository.ChargingMethodRepository;
import com.example.buserve.src.user.Role;
import com.example.buserve.src.user.SocialType;
import com.example.buserve.src.user.User;
import com.example.buserve.src.user.UserRepository;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner seedDatabase(UserRepository userRepository, ChargingMethodRepository chargingMethodRepository) {
        return args -> {
            User user1 = User.builder()
                    .email("user1@example.com")
                    .nickname("User1")
                    .role(Role.USER)
                    .socialType(SocialType.GOOGLE)
                    .busMoney(10000)
                    .build();
            userRepository.save(user1);

            ChargingMethod method1 = ChargingMethod.builder()
                    .name("신한은행")
                    .details("1111-1111-1111")
                    .user(user1)
                    .build();
            method1.setUser(user1);
            chargingMethodRepository.save(method1);
            userRepository.save(user1);

            ChargingMethod method2 = ChargingMethod.builder()
                    .name("신한은행")
                    .details("2222-2222-2222")
                    .user(user1)
                    .build();
            chargingMethodRepository.save(method2);

            User user2 = User.builder()
                    .email("user2@example.com")
                    .nickname("User2")
                    .role(Role.USER)
                    .socialType(SocialType.KAKAO)
                    .busMoney(2000)
                    .build();
            userRepository.save(user2);

            ChargingMethod method3 = ChargingMethod.builder()
                    .name("우리은행")
                    .details("3333-3333-3333")
                    .user(user2)
                    .build();
            chargingMethodRepository.save(method3);
        };
    }
}
