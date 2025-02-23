package com.frankit.product.global.exception.dto.response;

import com.frankit.product.global.exception.CommonException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> from(CommonException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(MessageFormat.format(e.getErrorCode().getMessage(), e.getArgs()))
                        .build()
                );
    }
}
