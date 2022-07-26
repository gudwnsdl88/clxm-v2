package com.clxm.service;

import com.clxm.controller.auth.dto.login.LoginResponse;
import com.clxm.domain.Player;
import com.clxm.repository.PlayerRepository;
import com.clxm.web.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse playerLoginWithEmailAndPassword(String email, String password){

        Player player = playerRepository.findByEmail(email).orElseThrow(()-> {
            throw new IllegalArgumentException("선수를 찾을 수 없습니다");
        });

        if(!passwordEncoder.matches(password, player.getPassword())){
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }

        String accessToken = jwtTokenProvider.createToken(player.getId());


        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken("");

        return loginResponse;

    }


}
