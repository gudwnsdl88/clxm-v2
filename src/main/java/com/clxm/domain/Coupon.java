package com.clxm.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean used = false;

    @Column(nullable = false)
    private int chargedDena;




    public Coupon(Long id) {
        this.id = id;
    }

    public Coupon(Long id, Player player) {
        this.id = id;
        this.player = player;
    }

    @Builder
    public Coupon(@NonNull LocalDateTime expiredAt, @NonNull Player player, @NonNull String code,
                  @NonNull Integer chargedDena) {
        this.expiredAt = expiredAt;
        this.player = player;
        this.code = code;
        this.chargedDena = chargedDena;
    }


    public void useCoupon() {
        this.used = true;
    }

}
