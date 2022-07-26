package com.clxm.controller.player;

import com.clxm.controller.player.dto.signUp.PlayerSignUpDto;
import com.clxm.domain.Player;
import com.clxm.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private PlayerService playerService;

    @Test
    public void 회원가입() throws Exception {


        //given
        PlayerSignUpDto dto = PlayerSignUpDto.builder()
                .email("testEmail@mail.com")
                .name("testName")
                .password("testPassword")
                .channelInfo(new ArrayList<>())
                .build();


        //when
        when(playerService.signUpPlayer(any(),any())).thenReturn(new Player(1L));

        Long savedPlayerId = playerController.signUpPlayer(dto);

        //then
        assertThat(savedPlayerId)
                .isEqualTo(1L);

    }


}