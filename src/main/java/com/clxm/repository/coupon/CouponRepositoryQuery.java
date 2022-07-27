package com.clxm.repository.coupon;

import com.clxm.domain.Coupon;
import com.clxm.domain.QCoupon;
import com.clxm.repository.coupon.dto.GetCouponByPlayerIdDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.clxm.domain.QCoupon.coupon;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    public List<GetCouponByPlayerIdDto> getCouponByPlayerId(Long playerId) {
        return queryFactory.select(
                        Projections.fields(GetCouponByPlayerIdDto.class, coupon.expiredAt, coupon.used, coupon.code,
                                coupon.chargedDena)
                )
                .from(coupon)
                .where(coupon.player.id.eq(playerId))
                .orderBy(coupon.expiredAt.desc())
                .fetch();

    }


}
