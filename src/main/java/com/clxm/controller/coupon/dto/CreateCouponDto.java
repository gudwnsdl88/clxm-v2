package com.clxm.controller.coupon.dto;

import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Builder
@Getter
public class CreateCouponDto {

    @NotEmpty
    private String code;

    @NotNull
    private Long playerId;

    @Future
    private LocalDateTime expiredAt;

    public Coupon toEntity(Player player){
        return Coupon.builder()
                .code(code)
                .expiredAt(expiredAt)
                .player(player)
                .build();
    }

}
