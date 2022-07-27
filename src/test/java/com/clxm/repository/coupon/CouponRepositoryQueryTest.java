package com.clxm.repository.coupon;

import com.clxm.config.TestConfig;
import com.clxm.domain.ChannelInfo;
import com.clxm.domain.Coupon;
import com.clxm.domain.Player;
import com.clxm.repository.PlayerRepository;
import com.clxm.repository.coupon.dto.GetCouponByPlayerIdDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@Import({TestConfig.class, CouponRepositoryQuery.class})
@Transactional
class CouponRepositoryQueryTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private CouponRepositoryQuery couponRepositoryQuery;
    @Autowired
    private CouponRepository couponRepository;

    private static final LocalDateTime DEFAULT_EXPIRED_AT = LocalDateTime.of(2022, 5, 27, 0, 0);
    private static final String DEFAULT_COUPON_CODE = "code";

    @BeforeAll
    public void init() {
        Player player = Player.builder()
                .password("1234")
                .name("name")
                .email("email@mail.com")
                .build();

        player.createRecommendInfo();
        player.addChannelInfo(ChannelInfo.builder()
                .player(player)
                .channelUrl("churl")
                .channelName("chName")
                .build());

        playerRepository.save(player);
    }

    @Test
    public void 선수_아이디로_쿠폰_조회() throws Exception {

        //given
        Player player = playerRepository.findByEmail("email@mail.com")
                .orElse(null);

        LocalDateTime expiredAt = DEFAULT_EXPIRED_AT;
        String couponCode = DEFAULT_COUPON_CODE;

        assert player != null;

        for(int i = 0; i <= 4; i++){
            Coupon coupon = Coupon.builder()
                    .expiredAt(expiredAt)
                    .chargedDena(1000)
                    .code(couponCode)
                    .player(player)
                    .build();

            couponRepository.save(coupon);

            expiredAt = expiredAt.minusDays(3);
            couponCode += couponCode += "~";
        }

        //when
        List<GetCouponByPlayerIdDto> result = couponRepositoryQuery.getCouponByPlayerId(
                player.getId());

        result.forEach(r -> {
            System.out.println(r.getExpiredAt().toString());
        });

        //then
        assertThat(result.get(0)
                .getExpiredAt()).isEqualTo(DEFAULT_EXPIRED_AT);


    }

}