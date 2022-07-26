package com.clxm.service;

import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.repository.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;



    @Test
    public void 쿠폰생성_성공() throws Exception {
        //given
        Player player = new Player(1L);
        Coupon coupon = Coupon.builder()
                .player(player)
                .code("testCouponCode")
                .expiredAt(LocalDateTime.now()
                        .plusHours(3))
                .build();

        //when
        when(couponRepository.save(coupon)).thenReturn(coupon);
        Coupon savedCoupon = couponService.createCoupon(coupon);

        //then
        assertThat(savedCoupon)
                .isEqualTo(coupon);

    }

    @Test
    public void 쿠폰생성_실패_중복코드() throws Exception {
        //given
        Player player = new Player(1L);
        Coupon coupon = Coupon.builder()
                .player(player)
                .code("testCouponCode")
                .expiredAt(LocalDateTime.now()
                        .plusHours(3))
                .build();

        //when
        when(couponRepository.findByCode(coupon.getCode())).thenReturn(Optional.of(new Coupon(1L)));

        assertThatThrownBy(() -> {
            couponService.createCoupon(coupon);
        });

        //then


    }

}