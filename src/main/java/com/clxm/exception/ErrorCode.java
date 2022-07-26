package com.clxm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PLAYER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "가입된 선수가 이미 존재합니다"),
    PLAYER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 아이디를 갖는 선수를 찾을 수 없습니다."),
    COUPON_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 코드로 생성된 쿠폰이 이미 존재합니다"),
    COUPON_IS_EXPIRED(HttpStatus.BAD_REQUEST, "쿠폰 유효기간이 지났습니다."),
    COUPON_NOT_VALID(HttpStatus.BAD_REQUEST, "이미 사용한 쿠폰입니다."),
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다");


    private final HttpStatus httpStatus;
    private final String detail;
}
