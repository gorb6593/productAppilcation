package com.frankit.product.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequestDto (

    @NotNull(message = "상품명은 필수입니다")
    String name,

    String description,

    @NotNull(message = "가격은 필수입니다")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다")
    BigDecimal price,

    @NotNull(message = "배송비는 필수입니다")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다")
    BigDecimal deliveryFee

) {}
