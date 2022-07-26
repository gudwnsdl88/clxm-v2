package com.clxm.controller.player;

import com.clxm.controller.player.dto.signUp.PlayerSignUpDto;
import com.clxm.domain.ChannelInfo;
import com.clxm.domain.Player;
import com.clxm.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("")
    public Long signUpPlayer(@Valid @RequestBody PlayerSignUpDto dto) throws Exception {

        Player player = dto.toEntity();

        List<ChannelInfo> channelInfoList = dto.getChannelInfo()
                .stream()
                .map(o -> {
                    return ChannelInfo.builder()
                            .channelUrl(o.getChannelUrl())
                            .channelName(o.getChannelName())
                            .build();
                })
                .collect(Collectors.toList());


        Player savedPlayer =  playerService.signUpPlayer(player, channelInfoList);


        return savedPlayer.getId();
    }


}
