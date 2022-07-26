package com.clxm.service;


import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.exception.CustomException;
import com.clxm.exception.ErrorCode;
import com.clxm.repository.CouponRepository;
import com.clxm.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;
    private final PlayerRepository playerRepository;


    public Coupon createCoupon(Coupon coupon) {

        Coupon existCoupon = couponRepository.findByCode(coupon.getCode())
                .orElse(null);

        if (existCoupon != null) {
            throw new CustomException(ErrorCode.COUPON_ALREADY_EXIST);
        }

        return couponRepository.save(coupon);
    }

    public Coupon useCoupon(Long couponId, Long playerId) {

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAYER_NOT_FOUND));

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->  new CustomException(ErrorCode.COUPON_NOT_FOUND));


        validBeforeUseCoupon(player, coupon);

        coupon.useCoupon();
        player.addDena(coupon.getChargedDena());

        return coupon;
    }

    private void validBeforeUseCoupon(Player player, Coupon coupon) {
        if (coupon.getExpiredAt()
                .isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.COUPON_IS_EXPIRED);
        }

        if (coupon.isUsed()) {
            throw new CustomException(ErrorCode.COUPON_NOT_VALID);
        }

        if (player.getId() != coupon.getPlayer()
                .getId()) {
            throw new IllegalArgumentException("로그인한 사용자가 사용할 수 없는 쿠폰입니다");
        }
    }

}
