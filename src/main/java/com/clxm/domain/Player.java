package com.clxm.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "player_id")
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<ChannelInfo> channelInfo = new ArrayList<>();

    @Column(nullable = false)
    private String profileImg = "DEFAULT.JPG";
    @Column(nullable = false)
    private Integer dena = 0;
    @Column(nullable = false)
    private Boolean auth = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_info_id", nullable = true)
    private BankInfo bankInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recommend_info_id", nullable = false)
    private RecommendInfo recommendInfo;

    @Builder
    public Player(String name, String email, String password
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Player(Long id) {
        this.id = id;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void createRecommendInfo() {
        if (this.recommendInfo != null) return;

        Random random = new Random();

        this.recommendInfo = RecommendInfo.builder()
                .code(random.nextInt(999999))
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()
                        .plusMonths(3))
                .build();
    }

    public void addChannelInfo(ChannelInfo channelInfo){
        this.channelInfo.add(channelInfo);
        channelInfo.setPlayer(this);
    }



}
