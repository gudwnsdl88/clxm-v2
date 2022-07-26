package com.clxm.controller.auth;

import com.clxm.controller.auth.dto.login.LoginDto;
import com.clxm.controller.auth.dto.login.LoginResponse;
import com.clxm.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/player/token")
    public LoginResponse loginWithPasswordAndEmail(@Valid @RequestBody LoginDto dto){

        return authService.playerLoginWithEmailAndPassword(dto.getEmail(), dto.getPassword());
    }

}
