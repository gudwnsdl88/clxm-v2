package com.clxm.controller.player.dto.signUp;

import com.clxm.domain.BankInfo;
import com.clxm.domain.ChannelInfo;
import com.clxm.domain.Player;
import com.clxm.domain.RecommendInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PlayerSignUpDto {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    @Valid
    private List<ChannelInfoDto> channelInfo;


    public Player toEntity() {
        return Player.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }


}
