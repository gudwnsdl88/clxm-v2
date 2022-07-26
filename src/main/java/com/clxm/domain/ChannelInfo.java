package com.clxm.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class ChannelInfo {
    @Id
    @GeneratedValue
    @Column(name = "channel_id")
    private Long id;

    private String channelName;
    private String channelUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Builder
    public ChannelInfo(String channelName, String channelUrl, Player player) {
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
