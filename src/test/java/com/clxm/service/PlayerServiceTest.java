package com.clxm.service;

import com.clxm.domain.ChannelInfo;
import com.clxm.domain.Player;
import com.clxm.repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {


    @InjectMocks
    private PlayerService playerService;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;


    // * Mock
    @DisplayName("선수 회원가입 성공")
    @Test
    @Disabled
    public void 선수_회원가입() throws Exception {
        //given
        InitSignUpResult initSignUpResult = PlayerServiceTestSupport.initSignUpTest();

        Player player = initSignUpResult.getPlayer();
        ChannelInfo channelInfo = initSignUpResult.getChannelInfo();
        player.addChannelInfo(channelInfo);



        //when
        playerService.signUpPlayer(player, List.of(channelInfo));

        //then
        verify(passwordEncoder).encode("testPassword");
        verify(playerRepository).save(player);

    }


    // * Stub
    @Test
    public void 선수_회원가입_성공  () throws Exception {
        //given
        InitSignUpResult initSignUpResult = PlayerServiceTestSupport.initSignUpTest();
        ChannelInfo channelInfo = initSignUpResult.getChannelInfo();
        Player player = initSignUpResult.getPlayer();

        //when
        when(passwordEncoder.encode(player.getPassword())).thenReturn("ENCODED_PASSWORD");
        when(playerRepository.save(player)).thenReturn(player);

        Player savedPlayer = playerService.signUpPlayer(player, List.of(channelInfo));

        //then
        assertThat(savedPlayer)
                .isEqualTo(player);

    }

    @Test
    public void 선수_회원가입_실패_중복회원 () throws Exception {
        //given
        InitSignUpResult initSignUpResult = PlayerServiceTestSupport.initSignUpTest();
        ChannelInfo channelInfo = initSignUpResult.getChannelInfo();
        Player player = initSignUpResult.getPlayer();

        Player mockPlayer = Player.builder()
                .build();

        //when
        when(playerRepository.findByEmail(player.getEmail())).thenReturn(Optional.ofNullable(mockPlayer));

        assertThatThrownBy(() -> {
            playerService.signUpPlayer(player, List.of(channelInfo));
        });
    }

    @Test
    public void 선수_회원가입_실패_채널정보없음 () throws Exception {
        //given
        InitSignUpResult initSignUpResult = PlayerServiceTestSupport.initSignUpTest();
        Player player = initSignUpResult.getPlayer();

        //when
        assertThatThrownBy(() -> {
            playerService.signUpPlayer(player, new ArrayList<>());
        });
    }





}