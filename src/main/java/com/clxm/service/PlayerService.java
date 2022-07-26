package com.clxm.service;


import com.clxm.domain.ChannelInfo;
import com.clxm.domain.Player;
import com.clxm.exception.CustomException;
import com.clxm.exception.ErrorCode;
import com.clxm.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public Player signUpPlayer(Player player, List<ChannelInfo> channelInfoList) throws Exception {

        String encodedPassword = passwordEncoder.encode(player.getPassword());

        channelInfoList.forEach(c -> player.addChannelInfo(c));
        player.changePassword(encodedPassword);
        player.createRecommendInfo();

        validateBeforeSignUp(player);

        return playerRepository.save(player);
    }

    private void validateBeforeSignUp(Player player) throws Exception {
        Player existPlayer = playerRepository.findByEmail(player.getEmail()).orElse(null);

        if(existPlayer != null){
            throw new CustomException(ErrorCode.PLAYER_ALREADY_EXIST);
        }

        if(player.getChannelInfo().size() == 0){
            throw new IllegalArgumentException("ChannelInfo가 없습니다");
        }

        if(player.getRecommendInfo() == null){
            throw new IllegalArgumentException("RecommendInfo가 없습니다");
        }

    }

}
