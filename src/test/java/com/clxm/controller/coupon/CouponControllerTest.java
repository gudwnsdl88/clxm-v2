package com.clxm.controller.coupon;

import com.clxm.controller.coupon.dto.CreateCouponDto;
import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.repository.PlayerRepository;
import com.clxm.service.CouponService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    @InjectMocks
    private CouponController couponController;
    @Mock
    private CouponService couponService;
    @Mock
    private PlayerRepository playerRepository;


    @Test
    public void 쿠폰_생성_성공() throws Exception {

        //given
        CreateCouponDto dto = CreateCouponDto.builder()
                .code("testCouponCode")
                .expiredAt(LocalDateTime.now()
                        .plusMonths(3))
                .playerId(1L)
                .build();

        //when
        when(playerRepository.findById(dto.getPlayerId())).thenReturn(Optional.of(new Player(1L)));
        when(couponService.createCoupon(any())).thenReturn(new Coupon(1L));
        Long savedCouponId = couponController.CreateCoupon(dto);

        //then
        Assertions.assertThat(savedCouponId)
                .isEqualTo(1L);

    }


}