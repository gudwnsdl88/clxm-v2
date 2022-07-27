package com.clxm.controller.player;

import com.clxm.controller.player.dto.signUp.PlayerSignUpDto;
import com.clxm.domain.ChannelInfo;
import com.clxm.domain.Player;
import com.clxm.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;


    @GetMapping("/coupon")
    public Result getCouponByPlayerId(Principal principal) {
//        return new Result<List<GetCouponByPlayerIdDto>>(playerService.getCouponByPlayerId(
//                Long.valueOf(principal.getName())));

        return new Result(playerService.getCouponByPlayerId(Long.valueOf(principal.getName())));
    }


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


        Player savedPlayer = playerService.signUpPlayer(player, channelInfoList);


        return savedPlayer.getId();
    }


    @Data
    @AllArgsConstructor
    class Result<T> {
        private T result;
    }
}
