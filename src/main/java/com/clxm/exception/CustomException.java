package com.clxm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private  ErrorCode errorCode;

    @Override
    public String getMessage() {
        return errorCode.getDetail();
    }
}
