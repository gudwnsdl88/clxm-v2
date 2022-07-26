package com.clxm.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendInfo {

    @Id
    @GeneratedValue
    @Column(name = "recommend_info_id")
    private Long id;


    private int code;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public RecommendInfo(int code, LocalDateTime startDate, LocalDateTime endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
