package com.frankit.product.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("N001", 400, "잘못된 요청입니다.");

    private final String code;
    private final int status;
    private final String message;
}
