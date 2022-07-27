package com.clxm.controller.coupon;

import com.clxm.controller.coupon.dto.CreateCouponDto;
import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.repository.coupon.CouponRepository;
import com.clxm.repository.PlayerRepository;
import com.clxm.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    @InjectMocks
    private CouponController couponController;
    @Mock
    private CouponService couponService;
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private Principal principal;

    private static final long DEFAULT_PLAYER_ID = 1L;
    private static final long DEFAULT_COUPON_ID = 2L;
    private static final int DEFAULT_CHARGE_DENA = 1000;
    private static final LocalDateTime DEFAULT_EXPIRED_DATE = LocalDateTime.now()
            .plusMonths(3);
    private static final String DEFAULT_COUPON_CODE = "testCouponCode";

    @BeforeEach
    public void initPrincipal() {
        when(principal.getName()).thenReturn(String.valueOf(DEFAULT_PLAYER_ID));
    }


    @Test
    public void 쿠폰_생성_성공() throws Exception {

        //given
        CreateCouponDto dto = CreateCouponDto.builder()
                .code(DEFAULT_COUPON_CODE)
                .expiredAt(DEFAULT_EXPIRED_DATE)
                .chargedDena(DEFAULT_CHARGE_DENA)
                .build();

        //when
        when(playerRepository.findById(DEFAULT_PLAYER_ID)).thenReturn(
                Optional.of(new Player(DEFAULT_PLAYER_ID)));
        when(couponService.createCoupon(any())).thenReturn(new Coupon(DEFAULT_COUPON_ID));

        Long savedCouponId = couponController.CreateCoupon(dto, principal);

        //then
        assertThat(savedCouponId)
                .isEqualTo(DEFAULT_COUPON_ID);

    }

    @Test
    public void 쿠폰_생성_실패_선수_없음() throws Exception {
        //given
        CreateCouponDto dto = CreateCouponDto.builder()
                .code(DEFAULT_COUPON_CODE)
                .expiredAt(DEFAULT_EXPIRED_DATE)
                .build();


        //when
        when(playerRepository.findById(any())).thenReturn(null);

        assertThatThrownBy(() -> {
            couponController.CreateCoupon(dto, principal);
        });
    }


    @Test
    public void 쿠폰_사용_성공() throws Exception {

        Coupon coupon = new Coupon(DEFAULT_COUPON_ID);

        //given
        when(couponService.useCoupon(DEFAULT_COUPON_ID, DEFAULT_PLAYER_ID)).thenReturn(coupon);

        //when
        couponController.useCoupon(DEFAULT_COUPON_ID, principal);

        //then
        verify(couponService, times(1)).useCoupon(DEFAULT_COUPON_ID, DEFAULT_PLAYER_ID);


    }

}