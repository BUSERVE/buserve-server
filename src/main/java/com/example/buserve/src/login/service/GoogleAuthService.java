package com.example.buserve.src.login.service;

import com.example.buserve.src.login.client.ClientGoogle;
import com.example.buserve.src.login.dto.AuthRequest;
import com.example.buserve.src.login.dto.AuthResponse;
import com.example.buserve.src.login.jwt.AuthToken;
import com.example.buserve.src.login.jwt.AuthTokenProvider;
import com.example.buserve.src.user.User;
import com.example.buserve.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final ClientGoogle clientGoogle;
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        User googleMember = clientGoogle.getUserData(authRequest.getAccessToken());
        String socialId = googleMember.getSocialId();
        User user = userRepository.findBySocialId(socialId);

        AuthToken appToken = authTokenProvider.createUserAppToken(socialId);

        if (user == null) {
            userRepository.save(googleMember);
            return AuthResponse.builder()
                    .appToken(appToken.getToken())
                    .isNewMember(Boolean.TRUE)
                    .build();
        }

        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .isNewMember(Boolean.FALSE)
                .build();
    }
}
