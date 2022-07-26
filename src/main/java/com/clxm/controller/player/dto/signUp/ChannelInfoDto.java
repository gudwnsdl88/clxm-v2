package com.clxm.controller.player.dto.signUp;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class ChannelInfoDto {

    @NotEmpty
    private String channelName;

    @NotEmpty(message = "채널 URL이 없어요")
    private String channelUrl;

}
