package com.clxm.service;

import com.clxm.domain.ChannelInfo;
import com.clxm.domain.Player;

class InitSignUpResult {
    private Player player;
    private ChannelInfo channelInfo;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }
}


public class PlayerServiceTestSupport {

    public static InitSignUpResult initSignUpTest() {
        Player player = Player.builder()
                .name("testName")
                .password("testPassword")
                .email("testEmail@mail.com")
                .build();

        ChannelInfo channelInfo = ChannelInfo.builder()
                .channelName("testChannelName")
                .channelUrl("testChannelUrl")
                .build();


        InitSignUpResult result = new InitSignUpResult();

        result.setPlayer(player);
        result.setChannelInfo(channelInfo);

        return result;
    }

}
