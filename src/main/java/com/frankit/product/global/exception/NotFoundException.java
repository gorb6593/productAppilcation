package com.frankit.product.global.exception;

import static com.frankit.product.global.exception.ErrorCode.*;

public class NotFoundException extends CommonException {
    public NotFoundException(ErrorCode message) {
        super(NOT_FOUND, message);
    }
}
