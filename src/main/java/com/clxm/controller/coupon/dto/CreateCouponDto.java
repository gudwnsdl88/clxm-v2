package com.clxm.controller.coupon.dto;

import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Builder
@Getter
@ToString
public class CreateCouponDto {

    @NotEmpty
    private String code;

    @Future
    private LocalDateTime expiredAt;

    @Min(100)
    private int chargedDena;

    public Coupon toEntity(Player player){
        return Coupon.builder()
                .code(code)
                .expiredAt(expiredAt)
                .player(player)
                .chargedDena(chargedDena)
                .build();
    }

}
