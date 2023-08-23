package com.example.buserve.src.login.client;


import com.example.buserve.src.login.dto.GoogleUserResponse;
import com.example.buserve.src.login.exception.TokenValidFailedException;

import com.example.buserve.src.user.Role;
import com.example.buserve.src.user.SocialType;
import com.example.buserve.src.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class ClientGoogle implements ClientProxy {

    private final WebClient webClient;

    // TODO ADMIN 유저 생성 시 getAdminUserData 메소드 생성 필요
    @Override
    public User getUserData(String accessToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        return User.builder()
                .socialId(googleUserResponse.getSub())
                .nickname(googleUserResponse.getNickname())
                .email(googleUserResponse.getEmail())
                .provider(SocialType.GOOGLE)
                .role(Role.USER)
                .imageUrl(googleUserResponse.getPicture() != null ? googleUserResponse.getPicture() : "")
                .build();
    }
}
