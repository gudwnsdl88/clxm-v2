package com.clxm.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankInfo {

    @Id
    @GeneratedValue
    @Column(name = "bank_info_id")
    private Long id;

    private String owner;
    private String bankName;
    private String accountNumber;


}
