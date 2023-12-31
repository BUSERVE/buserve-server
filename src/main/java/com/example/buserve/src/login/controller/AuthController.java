package com.example.buserve.src.login.controller;



import com.example.buserve.src.login.dto.ApiResponse;
import com.example.buserve.src.login.dto.AuthRequest;
import com.example.buserve.src.login.dto.AuthResponse;
import com.example.buserve.src.login.jwt.AuthToken;
import com.example.buserve.src.login.jwt.AuthTokenProvider;
import com.example.buserve.src.login.jwt.JwtHeaderUtil;
import com.example.buserve.src.login.service.AuthService;
import com.example.buserve.src.login.service.GoogleAuthService;
import com.example.buserve.src.login.service.KakaoAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@Api(tags = {"로그인 관련 API"})
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoAuthService kakaoAuthService;
    private final GoogleAuthService googleAuthService;
    private final AuthTokenProvider authTokenProvider;
    private final AuthService authService;

    /**
     * KAKAO 소셜 로그인 기능
     * @return ResponseEntity<AuthResponse>
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 반환")
    @PostMapping(value = "/kakao")
    public ResponseEntity<AuthResponse> kakaoAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(kakaoAuthService.login(authRequest));
    }

    /**
     * GOOGLE 소셜 로그인 기능
     * @return ResponseEntity<AuthResponse>
     */
    @ApiOperation(value = "구글 로그인", notes = "구글 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 반환")
    @PostMapping(value = "/google")
    public ResponseEntity<AuthResponse> googleAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(googleAuthService.login(authRequest));
    }

    /**
     * appToken 갱신
     * @return ResponseEntity<AuthResponse>
     */
    @ApiOperation(value = "appToken 갱신", notes = "appToken 갱신")
    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken (HttpServletRequest request) {
        String appToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(appToken);
        if (!authToken.validate()) { // 형식에 맞지 않는 token
            return ApiResponse.forbidden(null);
        }

        AuthResponse authResponse = authService.updateToken(authToken);
        if (authResponse == null) { // token 만료
            return ApiResponse.forbidden(null);
        }
        return ApiResponse.success(authResponse);
    }
}