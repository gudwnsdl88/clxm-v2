package com.clxm.service;

import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.exception.CustomException;
import com.clxm.exception.ErrorCode;
import com.clxm.repository.CouponRepository;
import com.clxm.repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CouponRepository couponRepository;

    private final long DEFAULT_PLAYER_ID = 1L;
    private final long DEFAULT_COUPON_ID = 2L;
    private final int DEFAULT_DENA = 1000;
    private final String DEFAULT_COUPON_CODE = "testCouponCode";
    private final LocalDateTime DEFAULT_EXPIRED_AT = LocalDateTime.now()
            .plusMonths(3);


    @Test
    public void 쿠폰생성_성공() throws Exception {
        //given
        Player player = new Player(DEFAULT_PLAYER_ID);
        Coupon coupon = Coupon.builder()
                .player(player)
                .code(DEFAULT_COUPON_CODE)
                .expiredAt(DEFAULT_EXPIRED_AT)
                .chargedDena(DEFAULT_DENA)
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
        Player player = new Player(DEFAULT_PLAYER_ID);
        Coupon coupon = Coupon.builder()
                .player(player)
                .code(DEFAULT_COUPON_CODE)
                .expiredAt(DEFAULT_EXPIRED_AT)
                .chargedDena(DEFAULT_DENA)
                .build();

        //when
        when(couponRepository.findByCode(coupon.getCode())).thenReturn(
                Optional.of(new Coupon(DEFAULT_PLAYER_ID)));

        assertThatThrownBy(() -> {
            couponService.createCoupon(coupon);
        }).hasMessage(ErrorCode.COUPON_ALREADY_EXIST.getDetail());

    }

    @Test
    public void 쿠폰_사용_성공() throws Exception {

        //given
        Player player = new Player(2L);

        Coupon coupon = Coupon.builder()
                .code(DEFAULT_COUPON_CODE)
                .chargedDena(DEFAULT_DENA)
                .expiredAt(DEFAULT_EXPIRED_AT)
                .player(player)
                .build();

        when(playerRepository.findById(DEFAULT_PLAYER_ID)).thenReturn(Optional.of(player));
        when(couponRepository.findById(DEFAULT_COUPON_ID)).thenReturn(Optional.of(coupon));

        //when
        couponService.useCoupon(DEFAULT_COUPON_ID, DEFAULT_PLAYER_ID);

        //then
        assertThat(player
                .getDena()).isEqualTo(DEFAULT_DENA);

        assertThat(coupon.isUsed()).isEqualTo(true);
    }

    @Test
    public void 쿠폰_사용_실패_만료() throws Exception {
        //given
        Player player = new Player(DEFAULT_PLAYER_ID);
        Coupon coupon = Coupon.builder()
                .code(DEFAULT_COUPON_CODE)
                .chargedDena(DEFAULT_DENA)
                .expiredAt(LocalDateTime.now()
                        .minusDays(7))
                .player(new Player(0))
                .build();

        when(playerRepository.findById(DEFAULT_PLAYER_ID)).thenReturn(Optional.of(player));
        when(couponRepository.findById(DEFAULT_COUPON_ID)).thenReturn(Optional.of(coupon));

        //when
        assertThatThrownBy(() -> {
            couponService.useCoupon(DEFAULT_COUPON_ID, DEFAULT_PLAYER_ID);
        }).hasMessage(ErrorCode.COUPON_IS_EXPIRED.getDetail());

    }

    @Test
    public void 쿠폰_사용_실패_이미_사용한_쿠폰() throws Exception {
        //given
        Player player = new Player(DEFAULT_PLAYER_ID);
        Coupon coupon = Coupon.builder()
                .code(DEFAULT_COUPON_CODE)
                .chargedDena(DEFAULT_DENA)
                .expiredAt(DEFAULT_EXPIRED_AT)
                .player(new Player(0))
                .build();

        coupon.useCoupon();

        when(playerRepository.findById(DEFAULT_PLAYER_ID)).thenReturn(Optional.of(player));
        when(couponRepository.findById(DEFAULT_COUPON_ID)).thenReturn(Optional.of(coupon));


        //when
        assertThatThrownBy(() -> {
            couponService.useCoupon(DEFAULT_COUPON_ID,DEFAULT_PLAYER_ID);
        }).hasMessage(ErrorCode.COUPON_NOT_VALID.getDetail());


    }


}