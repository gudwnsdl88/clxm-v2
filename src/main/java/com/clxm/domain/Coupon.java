package com.clxm.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {
    @Id @GeneratedValue
    @Column(name ="coupon_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id",nullable = false)
    private Player player;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean used = false;

    public Coupon(Long id) {
        this.id = id;
    }

    @Builder
    public Coupon(LocalDateTime expiredAt, Player player, String code) {
        this.expiredAt = expiredAt;
        this.player = player;
        this.code = code;
    }
}
