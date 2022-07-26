package com.clxm.controller.coupon;

import com.clxm.controller.coupon.dto.CreateCouponDto;
import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.exception.CustomException;
import com.clxm.exception.ErrorCode;
import com.clxm.repository.CouponRepository;
import com.clxm.repository.PlayerRepository;
import com.clxm.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final PlayerRepository playerRepository;
    private final CouponService couponService;
    private final CouponRepository couponRepository;

    @PostMapping("")
    public Long CreateCoupon(@Valid @RequestBody CreateCouponDto dto, Principal principal){

        Player player = findPlayer(Long.valueOf(principal.getName()));
        Coupon coupon = dto.toEntity(player);

        Coupon savedCoupon = couponService.createCoupon(coupon);

        return savedCoupon.getId();
    }

    @PutMapping("/{couponId}/used")
    public Long useCoupon(@PathVariable Long couponId, Principal principal){

        long playerId = Long.parseLong(principal.getName());

        Coupon usedCoupon = couponService.useCoupon(couponId, playerId);

        return usedCoupon.getId();
    }

    private Player findPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElse(null);
        if(player == null){
            throw new CustomException(ErrorCode.PLAYER_NOT_FOUND);
        }
        return player;
    }

    private Coupon findCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElse(null);

        if(coupon == null){
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }
        return coupon;
    }


}
