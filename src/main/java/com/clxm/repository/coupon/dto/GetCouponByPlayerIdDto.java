package com.clxm.repository.coupon.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetCouponByPlayerIdDto {

    private LocalDateTime expiredAt;
    private boolean used;
    private String code;
    private int chargedDena;


    public GetCouponByPlayerIdDto() {
    }


    public GetCouponByPlayerIdDto(LocalDateTime expiredAt, boolean used, String code, int chargedDena) {
        this.expiredAt = expiredAt;
        this.used = used;
        this.code = code;
        this.chargedDena = chargedDena;
    }


}
