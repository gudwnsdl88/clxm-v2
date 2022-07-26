package com.clxm.controller.coupon;

import com.clxm.controller.coupon.dto.CreateCouponDto;
import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.exception.CustomException;
import com.clxm.exception.ErrorCode;
import com.clxm.repository.PlayerRepository;
import com.clxm.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final PlayerRepository playerRepository;
    private final CouponService couponService;

    @PostMapping("")
    public Long CreateCoupon(@Valid @RequestBody CreateCouponDto dto){

        Player player = playerRepository.findById(dto.getPlayerId())
                .orElse(null);

        if(player == null){
            throw new CustomException(ErrorCode.PLAYER_NOT_FOUND);
        }

        Coupon coupon = dto.toEntity(player);

        Coupon savedCoupon = couponService.createCoupon(coupon);

        return savedCoupon.getId();
    }


}
