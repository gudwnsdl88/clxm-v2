package com.clxm.service;


import com.clxm.domain.Coupon;
import com.clxm.exception.CustomException;
import com.clxm.exception.ErrorCode;
import com.clxm.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;


    public Coupon createCoupon(Coupon coupon){

        Coupon existCoupon = couponRepository.findByCode(coupon.getCode())
                .orElse(null);

        if(existCoupon != null){
            throw new CustomException(ErrorCode.COUPON_ALREADY_EXIST);
        }

        return couponRepository.save(coupon);
    }

}
